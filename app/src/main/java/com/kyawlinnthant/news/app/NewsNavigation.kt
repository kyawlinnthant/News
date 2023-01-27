package com.kyawlinnthant.news.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kyawlinnthant.news.presentation.detail.DetailScreen
import com.kyawlinnthant.news.presentation.home.HomeScreen

@Composable
fun NewsGraph() {
    NavHost(
        navController = rememberNavController(),
        startDestination = Destinations.Home
    ) {
        composable(
            route = Destinations.Home
        ) {
            HomeScreen()
        }
        composable(
            route = Destinations.Detail
        ) {
            DetailScreen()
        }
    }
}
