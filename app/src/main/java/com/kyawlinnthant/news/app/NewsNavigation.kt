package com.kyawlinnthant.news.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kyawlinnthant.news.presentation.detail.DetailScreen
import com.kyawlinnthant.news.presentation.home.HomeScreen

@Composable
fun NewsGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Home
    ) {
        composable(
            route = Destinations.Home
        ) {
            HomeScreen(
                navController = navController
            )
        }
        composable(
            route = Destinations.Detail + "/{id}",
            arguments = listOf(
                navArgument(name = "id") { type = NavType.LongType }
            )
        ) {
            val id = it.arguments?.getString("id")
            it.savedStateHandle["id"] = id
            DetailScreen()
        }
    }
}
