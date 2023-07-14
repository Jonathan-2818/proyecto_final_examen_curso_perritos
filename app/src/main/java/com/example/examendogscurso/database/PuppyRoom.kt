package com.example.proyectofinal.dataLayer.repositories.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectofinal.dataLayer.entities.PuppyEntity
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [PuppyEntity::class],
    version = 2
) abstract class PuppyRoomDb : RoomDatabase() {

    abstract fun puppyDao(): PuppyDao companion object {

        @Volatile
        private var INSTANCE: PuppyRoomDb? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ):PuppyRoomDb{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PuppyRoomDb::class.java,
                    "dog.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}