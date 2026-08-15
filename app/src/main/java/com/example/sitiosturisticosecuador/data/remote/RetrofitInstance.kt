package com.example.sitiosturisticosecuador.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TouristApiService {
    @GET("api/turismo") // Reemplaza con tu endpoint real
    suspend fun getTouristPlaces(): List<TouristPlace>
}

object RetrofitInstance {
    val api: TouristApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://tu-api-url.com/") // Reemplaza con tu URL base
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TouristApiService::class.java)
    }
}

data class TouristPlace(
    val id: String,
    val name: String,
    val province: String,
    val description: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val isFavorite: Boolean = false
)