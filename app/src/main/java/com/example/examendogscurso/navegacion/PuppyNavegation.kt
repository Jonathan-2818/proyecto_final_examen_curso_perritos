package com.example.proyectofinal.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.examendogscurso.screens.PuppyScreenWelcome
import com.example.proyectofinal.domainLayer.viewModels.ViewModelDog
import com.example.proyectofinal.uiLayer.screens.PuppyScreen
import com.example.proyectofinal.uiLayer.screens.PuppyScreenDetail
import com.example.proyectofinal.uiLayer.screens.PuppyScreenFav


sealed class Routes(val route: String) {
    object Random : Routes("random")
    object Detail : Routes("detail")
    object Favorite : Routes("favorite")
    object Welcome : Routes("welcome")
}

@Composable
fun PuppyNavigation(viewModelDog: ViewModelDog) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Welcome.route // Cambia la ruta de inicio a la nueva ruta de bienvenida
    ) {
        composable(Routes.Welcome.route) {
            PuppyScreenWelcome(navController = navController)
        }

        composable(Routes.Random.route) {
            PuppyScreen(
                navController = navController,
                viewModel = viewModelDog
            )
        }

        composable(
            "${Routes.Detail.route}/{breed}",
            arguments = listOf(navArgument("breed") { type = NavType.StringType })
        ) {
            val breed = it.arguments?.getString("breed") ?: ""
            PuppyScreenDetail(
                breed,
                viewModel = viewModelDog
            )
        }

        composable(Routes.Favorite.route) {
            PuppyScreenFav(
                navController = navController,
                viewModel = viewModelDog
            )
        }
    }
}