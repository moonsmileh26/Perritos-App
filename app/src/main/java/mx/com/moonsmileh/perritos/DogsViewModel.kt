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


sealed class DogsUiState {
    data class Success(val dogs: List<String>) : DogsUiState()
    data class Error(val exception: Throwable) : DogsUiState()
}

class DogsViewModel : ViewModel() {

    private val _dogsState = MutableStateFlow(DogsUiState.Success(emptyList()))
    var dogsState: StateFlow<DogsUiState> = _dogsState

    fun fetchUsers(query: String) {
        viewModelScope.launch {
            ApiBuilder.api.getDogsByBreed(query).enqueue(object : Callback<DogsResponse> {
                override fun onResponse(
                    call: Call<DogsResponse>,
                    response: Response<DogsResponse>
                ) {
                    val dogs = response.body()?.images ?: emptyList()
                    _dogsState.value = DogsUiState.Success(dogs)
                }

                override fun onFailure(call: Call<DogsResponse>, t: Throwable) {
                    //_dogsState.value = DogsUiState.Error(t)
                }
            })
        }
    }
}