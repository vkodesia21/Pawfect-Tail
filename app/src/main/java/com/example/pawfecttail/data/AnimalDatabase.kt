package com.example.pawfecttail.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pawfecttail.AnimalApplication

@Database(entities = [Animal::class], version = 1, exportSchema = false)
abstract class AnimalDatabase: RoomDatabase() {

    abstract fun getAnimalDao(): AnimalDao

    companion object{

        @Volatile
        private var INSTANCE: AnimalDatabase?=null

        fun getDatabase(context: Context): AnimalDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimalDatabase::class.java,
                    "animal_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}