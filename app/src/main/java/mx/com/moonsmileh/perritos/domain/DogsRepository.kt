package mx.com.moonsmileh.perritos.domain

interface DogsRepository {
    suspend fun getDogsByBreed(breed: String): DogModel?

}