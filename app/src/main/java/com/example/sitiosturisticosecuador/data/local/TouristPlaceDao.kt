package com.example.sitiosturisticosecuador.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TouristPlaceDao {
    @Query("SELECT * FROM favorite_places")
    fun getFavoritePlaces(): Flow<List<TouristPlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(place: TouristPlaceEntity)

    @Delete
    suspend fun deleteFavorite(place: TouristPlaceEntity)
}