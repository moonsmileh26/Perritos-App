package mx.com.moonsmileh.perritos.domain.usecase

import mx.com.moonsmileh.perritos.domain.DogsRepository
import javax.inject.Inject

class GetDogsUseCase @Inject constructor(private val repository: DogsRepository) {

    suspend operator fun invoke(breed: String) = repository.getDogsByBreed(breed)

}