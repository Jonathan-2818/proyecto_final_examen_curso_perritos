package com.example.proyectofinal.dataLayer.repositories.db

import androidx.annotation.WorkerThread
import com.example.proyectofinal.dataLayer.entities.PuppyEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow



class PuppyRepositoryRoom(private val puppyDao: PuppyDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val allDogs: Flow<List<PuppyEntity>> = puppyDao.getAll()

    @WorkerThread
    fun insert(dog: PuppyEntity){
        puppyDao.insert(dog)
    }


    suspend fun delete(breed: String){
        puppyDao.delete(breed)
    }
}