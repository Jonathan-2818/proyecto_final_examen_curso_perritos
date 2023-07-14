package com.example.proyectofinal.dataLayer.repositories.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.proyectofinal.dataLayer.entities.PuppyEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PuppyDao {
    @Insert()
    fun insert(dog: PuppyEntity)

    @Query("SELECT * FROM perros")
    fun getAll(): Flow<List<PuppyEntity>>

    @Query("Delete from perros WHERE breed = :breed ")
    suspend fun delete(breed: String)
}