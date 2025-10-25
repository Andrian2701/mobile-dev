package com.example.lab_3

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    object LocationsList : Screen("locations-list")
    object LocationDetails : Screen("location-details/{name}/{desc}") {
        fun createRoute(name: String, desc: String) =
            "location-details/${name}/${desc}"
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.LocationsList.route) {
        composable(Screen.LocationsList.route) {
            LocationsListPage(navController)
        }
        composable(Screen.LocationDetails.route) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: "Unknown"
            val desc = backStackEntry.arguments?.getString("desc") ?: "No description"
            LocationDetailsPage(navController, name, desc)
        }
    }
}
