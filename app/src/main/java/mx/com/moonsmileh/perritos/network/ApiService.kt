package mx.com.moonsmileh.perritos.network

import mx.com.moonsmileh.perritos.DogsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{query}/images")
    fun getDogsByBreed(@Path("query") query: String): Call<DogsResponse>
}