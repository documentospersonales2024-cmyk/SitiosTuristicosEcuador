package com.example.sitiosturisticosecuador.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_places")
data class TouristPlaceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val province: String,
    val description: String,
    val imageUrl: String,
    val latitude: Double,
    val longitude: Double
)