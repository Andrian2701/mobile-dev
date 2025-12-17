package com.example.lab_4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab_4.data.AppSeeder
import com.example.lab_4.navigation.Routes
import com.example.lab_4.ui.screens.LabDetailsScreen
import com.example.lab_4.ui.screens.LabsScreen
import com.example.lab_4.ui.screens.SubjectsScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val seeder: AppSeeder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Seed once (AC)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                seeder.seedIfNeeded()
            }
        }

        setContent {
            val navController = rememberNavController()

            Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier) {
                NavHost(navController = navController, startDestination = Routes.SUBJECTS) {

                    composable(Routes.SUBJECTS) {
                        SubjectsScreen(navController = navController)
                    }

                    composable(
                        route = "${Routes.LABS}/{subjectId}",
                        arguments = listOf(navArgument("subjectId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val subjectId = backStackEntry.arguments?.getInt("subjectId") ?: 0
                        LabsScreen(navController = navController, subjectId = subjectId)
                    }

                    composable(
                        route = "${Routes.LAB_DETAILS}/{labId}",
                        arguments = listOf(navArgument("labId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val labId = backStackEntry.arguments?.getInt("labId") ?: 0
                        LabDetailsScreen(navController = navController, labId = labId)
                    }
                }
            }
        }
    }
}
