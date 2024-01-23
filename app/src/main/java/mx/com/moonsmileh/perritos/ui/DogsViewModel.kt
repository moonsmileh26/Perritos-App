package mx.com.moonsmileh.perritos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mx.com.moonsmileh.perritos.data.response.ResponseError
import mx.com.moonsmileh.perritos.data.response.ResponseFailure
import mx.com.moonsmileh.perritos.domain.usecase.GetDogsUseCase
import javax.inject.Inject


open class UiState {
    data class Success(val dogs: List<String>) : UiState()
    data class Failure(val error: kotlin.Error) : UiState()
    data class Loading(val isLoading: Boolean) : UiState()
    data class Error(val exception: Throwable) : UiState()
}

@HiltViewModel
class DogsViewModel @Inject constructor(private val getDogsUseCase: GetDogsUseCase) : ViewModel() {

    private val _dogsState = MutableStateFlow(UiState())
    var dogsState: StateFlow<UiState> = _dogsState

    fun fetchUsers(query: String) {
        viewModelScope.launch {
            _dogsState.value = UiState.Loading(true)

            val result = withContext(Dispatchers.IO) {
                getDogsUseCase(query)
            }
            if (result != null) {
                val dogs = getDogsUseCase.invoke(query)?.images ?: emptyList()
                if (dogs.isEmpty()) {
                    _dogsState.value = UiState.Failure(ResponseFailure())
                } else {
                    _dogsState.value = UiState.Success(dogs)
                }
            } else {
                _dogsState.value = UiState.Error(ResponseError())
            }
            _dogsState.value = UiState.Loading(false)
        }
    }
}