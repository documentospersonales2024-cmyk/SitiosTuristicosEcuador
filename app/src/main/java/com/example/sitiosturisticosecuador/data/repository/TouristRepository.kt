package com.example.sitiosturisticosecuador.data.repository

import com.example.sitiosturisticosecuador.data.local.TouristPlaceDao
import com.example.sitiosturisticosecuador.data.local.TouristPlaceEntity
import com.example.sitiosturisticosecuador.data.remote.RetrofitInstance
import com.example.sitiosturisticosecuador.data.remote.TouristPlace
import kotlinx.coroutines.flow.Flow

class TouristRepository(private val dao: TouristPlaceDao) {

    suspend fun getRemotePlaces(): List<TouristPlace> {
        // Lista de respaldo local mientras no tengas un servidor activo
        return listOf(
            TouristPlace(
                id = "1",
                name = "Mitad del Mundo",
                province = "Pichincha",
                description = "Monumento que marca la línea equinoccial.",
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5QYNasxxh5-VI27pas9PEMeVE9PbKOtori28PqiER-A&s=10",
                latitude = -0.0028,
                longitude = -78.4553
            ),
        )
    }

    val favorites: Flow<List<TouristPlaceEntity>> = dao.getFavoritePlaces()

    suspend fun addFavorite(place: TouristPlaceEntity) {
        dao.insertFavorite(place)
    }

    suspend fun removeFavorite(place: TouristPlaceEntity) {
        dao.deleteFavorite(place)
    }
}