package mx.com.moonsmileh.perritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import mx.com.moonsmileh.perritos.network.ApiBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


open class UiState {
    data class Success(val dogs: List<String>) : UiState()
    data class Loading(val isLoading: Boolean) : UiState()
    data class Error(val exception: Throwable) : UiState()
}

class DogsViewModel : ViewModel() {

    private val _dogsState = MutableStateFlow(UiState())
    var dogsState: StateFlow<UiState> = _dogsState

    fun fetchUsers(query: String) {
        viewModelScope.launch {
            _dogsState.value = UiState.Loading(true)

            ApiBuilder.api.getDogsByBreed(query).enqueue(object : Callback<DogsResponse> {
                override fun onResponse(
                    call: Call<DogsResponse>,
                    response: Response<DogsResponse>
                ) {
                    if (response.isSuccessful) {
                        val dogs = response.body()?.images ?: emptyList()
                        _dogsState.value = UiState.Success(dogs)
                        _dogsState.value = UiState.Loading(false)
                    } else {
                        _dogsState.value = UiState.Error(ResponseError())
                    }
                }

                override fun onFailure(call: Call<DogsResponse>, t: Throwable) {
                    _dogsState.value = UiState.Error(t)
                }
            })
        }
    }
}