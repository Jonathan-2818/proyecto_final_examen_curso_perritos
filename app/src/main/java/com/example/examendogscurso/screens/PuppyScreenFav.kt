package com.example.proyectofinal.uiLayer.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.examendogscurso.ui.theme.ExamenDogsCursoTheme
import com.example.proyectofinal.domainLayer.viewModels.ViewModelDog
import com.example.proyectofinal.ui.componens.PuppyItemCard
import com.example.proyectofinal.ui.navegacion.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuppyScreenFav(navController: NavHostController, viewModel: ViewModelDog) {
    val searchQueryState = remember { mutableStateOf("") }
    val favoriteDogsBreedsState: MutableState<List<String>?> = remember {
        mutableStateOf(viewModel.roomDogBreeds.value)
    }

    viewModel.roomDogBreeds.observeForever {
        favoriteDogsBreedsState.value = it
    }

    viewModel.getRoomAllDogs()

    ExamenDogsCursoTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 30.dp),
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Me Encanta", fontSize = 28.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = searchQueryState.value,
                    onValueChange = { searchQueryState.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Busca el perrito que te encanta") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { /* handle search action */ })
                )


                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn {
                    val filteredBreeds = favoriteDogsBreedsState.value?.filter { breed ->
                        breed.contains(searchQueryState.value, ignoreCase = true)
                    } ?: emptyList()

                    items(filteredBreeds) { breed ->
                        PuppyItemCard(breed) {
                            navController.navigate("${Routes.Detail.route}/$breed")
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        }
    }
}