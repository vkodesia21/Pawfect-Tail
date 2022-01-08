package com.example.pawfecttail.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface AnimalDao {

    @Query("Select * from animal order by id Desc")
    fun getAllAnimals(): Flow<List<Animal>>

    @Query("SELECT * from animal WHERE id = :id")
    fun getAnimal(id: Int): Flow<Animal>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(animal: Animal)

    @Delete
    suspend fun delete(animal: Animal)

    @Update
    suspend fun update(animal: Animal)
}