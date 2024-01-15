package mx.com.moonsmileh.perritos.data.network

import mx.com.moonsmileh.perritos.data.response.DogsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{query}/images")
    suspend fun getDogsByBreed(@Path("query") query: String): DogsResponse
}