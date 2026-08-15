package com.example.sitiosturisticosecuador.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TouristPlaceEntity::class], version = 1)
abstract class TouristDatabase : RoomDatabase() {
    abstract fun touristPlaceDao(): TouristPlaceDao

    companion object {
        @Volatile
        private var INSTANCE: TouristDatabase? = null

        fun getDatabase(context: Context): TouristDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TouristDatabase::class.java,
                    "tourist_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}