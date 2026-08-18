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
            TouristPlace(
                id = "2",
                name = "Volcán Quilotoa",
                province = "Cotopaxi",
                description = "Impresionante caldera volcánica con una laguna esmeralda.",
                imageUrl = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/17/20/e7/0b/quilatoa-and-wild-flowers.jpg?w=1200&h=-1&s=1",
                latitude = -0.8584,
                longitude = -78.9038
            ),
            TouristPlace(
                id = "3",
                name = "Mirador de la Virgen del Panecillo",
                province = "Pichincha",
                description = "Los visitantes pueden disfrutar de una vista panorámica espectacular de la ciudad de Quito y sus alrededores.",
                imageUrl = "https://ec.viajandox.com/uploads/Virgen%20de%20El%20Panecillo_1.jpg",
                latitude = -0.227982,
                longitude = -78.519040
            ),
            TouristPlace(
                id = "4",
                name = "Pailón del Diablo",
                province = "Tungurahua",
                description = "Encontramos diversos paisajes y el reencuentro con la naturaleza.",
                imageUrl = "https://cdn.getyourguide.com/image/format=auto%2Cfit=crop%2Cgravity=auto%2Cquality=60%2Cheight=440%2Cdpr=1/tour_img/cb52fec84cd311e9ea82ab4449339c6e9b0b1c512088c5078a4dd4c0ce8e5d39.jpg",
                latitude = -1.395641,
                longitude = -78.286858
            )
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