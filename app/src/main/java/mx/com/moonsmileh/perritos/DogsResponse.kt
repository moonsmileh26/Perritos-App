package mx.com.moonsmileh.perritos

import com.google.gson.annotations.SerializedName

data class DogsResponse(
    var status: String,
    @SerializedName("message") var images: List<String>
)

