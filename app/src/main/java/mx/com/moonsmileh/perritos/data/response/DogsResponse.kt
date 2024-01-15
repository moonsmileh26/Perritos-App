package mx.com.moonsmileh.perritos.data.response

import mx.com.moonsmileh.perritos.domain.DogModel

data class DogsResponse(
    var status: String,
    var message: List<String>
) {
    fun toDomain(): DogModel {
        return DogModel(message)
    }
}

