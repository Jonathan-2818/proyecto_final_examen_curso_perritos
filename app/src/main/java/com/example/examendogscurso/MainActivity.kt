package com.example.examendogscurso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.proyectofinal.dataLayer.repositories.db.PuppyRepositoryRoom
import com.example.proyectofinal.dataLayer.repositories.db.PuppyRoomDb
import com.example.proyectofinal.domainLayer.viewModels.ViewModelDog
import com.example.proyectofinal.domainLayer.viewModels.ViewModelFactory
import com.example.proyectofinal.ui.navegacion.PuppyNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    val scope = CoroutineScope(Dispatchers.IO)

    val database by lazy { PuppyRoomDb.getDatabase(
        context = this,
        scope = scope)
    }

    val repository by lazy { PuppyRepositoryRoom(database.puppyDao()) }

    private val viewModelDog by viewModels<ViewModelDog>{
        ViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            PuppyNavigation(viewModelDog = viewModelDog)

        }
    }
}
