package com.example.proyectofinal.uiLayer.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.examendogscurso.R
import com.example.examendogscurso.ui.theme.ExamenDogsCursoTheme
import com.example.proyectofinal.dataLayer.model.Dog
import com.example.proyectofinal.domainLayer.viewModels.ViewModelDog

@Composable
fun PuppyScreenDetail(
    breed: String,
    viewModel: ViewModelDog
) {

    val dogImgState: MutableState<List<String>> = remember {
        mutableStateOf(emptyList())
    }
    viewModel.dogsDetail.observeForever {
        dogImgState.value = it
    }

    val favoriteDogState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    viewModel.roomDogBreeds.observeForever {
        favoriteDogState.value = validateFavorite(breed, viewModel.roomDogBreeds.value?.toList() ?: emptyList())
    }

    viewModel.getDogBreedImages(breed = breed)
    viewModel.getRoomAllDogs()

    ExamenDogsCursoTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                            ) {
                                Text(text = "Raza:", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                Text(text = breed, fontSize = 24.sp)
                            }
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable {
                                            val dog = Dog(breed, dogImgState.value[0])
                                            if (favoriteDogState.value) {
                                                deleteFavorite(dog, viewModel)
                                            } else {
                                                insertFavorite(dog, viewModel)
                                            }
                                        },
                                    painter =
                                    if (favoriteDogState.value)
                                        painterResource(id = R.drawable.favorite2)
                                    else
                                        painterResource(id = R.drawable.favorite1),
                                    contentDescription = "Me Encanta",
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                }

                if (dogImgState.value.isNotEmpty()) {
                    items(dogImgState.value) { imgUrl ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(bottom = 30.dp)
                        ) {
                            AsyncImage(
                                model = imgUrl,
                                contentDescription = "Translated description of what the image contains",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

fun insertFavorite(dog: Dog, viewModel: ViewModelDog) {
    viewModel.insertRoomDog(dog)
}

fun deleteFavorite(dog: Dog, viewModel: ViewModelDog) {
    viewModel.deleteRoomDog(dog)
}
fun validateFavorite(breed: String, favoritesDogs: List<String>): Boolean {
    return favoritesDogs.contains(breed)
}