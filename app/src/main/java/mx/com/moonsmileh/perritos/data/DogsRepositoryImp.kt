package mx.com.moonsmileh.perritos.data

import android.util.Log
import mx.com.moonsmileh.perritos.data.network.ApiService
import mx.com.moonsmileh.perritos.domain.DogModel
import mx.com.moonsmileh.perritos.domain.DogsRepository
import javax.inject.Inject

class DogsRepositoryImp @Inject constructor(private val apiService: ApiService) : DogsRepository {
    override suspend fun getDogsByBreed(breed: String): DogModel? {
        runCatching {
            apiService.getDogsByBreed(breed)
        }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.d("DogsRepositoryImp", "On failure getting dogs with breed $breed") }
        return null
    }
}