package com.example.proyectofinal.uiLayer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.examendogscurso.ui.theme.ExamenDogsCursoTheme
import com.example.proyectofinal.domainLayer.viewModels.ViewModelDog
import com.example.proyectofinal.ui.componens.PuppyItemCard

import com.example.proyectofinal.ui.navegacion.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuppyScreen(navController: NavHostController, viewModel: ViewModelDog) {
    val searchQueryState = remember { mutableStateOf("") }
    val dogsBreedsState: MutableState<List<String>?> = remember {
        mutableStateOf(viewModel.dogBreeds.value)
    }

    // Observador cuando la lista de perros cambia
    viewModel.dogBreeds.observeForever {
        dogsBreedsState.value = it
    }

    viewModel.getDogBreeds()

    ExamenDogsCursoTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Lista Perritos", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(50.dp))
                    IconButton(
                        onClick = { navController.navigate(Routes.Favorite.route) },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary
                            ),
                        content = {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Me Encanta",
                                tint = Color.White
                            )
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = searchQueryState.value,
                    onValueChange = { searchQueryState.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Buscar raza de perro") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                    keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { /* handle search action */ })
                )

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn {
                    val filteredBreeds = dogsBreedsState.value?.filter { breed ->
                        breed.contains(searchQueryState.value, ignoreCase = true)
                    } ?: emptyList()

                    items(filteredBreeds) { breed ->
                        PuppyItemCard(breed) {
                            navController.navigate("${Routes.Detail.route}/$breed")
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}
