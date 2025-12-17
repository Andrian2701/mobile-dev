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
import com.example.lab_4.data.AppDatabase
import com.example.lab_4.data.LabEntity
import com.example.lab_4.data.LabStatus
import com.example.lab_4.data.SubjectEntity
import com.example.lab_4.navigation.Routes
import com.example.lab_4.ui.screens.LabDetailsScreen
import com.example.lab_4.ui.screens.LabsScreen
import com.example.lab_4.ui.screens.SubjectsScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AppDatabase.getInstance(this)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                seedIfNeeded(db)
            }
        }

        setContent {
            val navController = rememberNavController()

            Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.SUBJECTS
                ) {
                    composable(Routes.SUBJECTS) {
                        SubjectsScreen(db = db, navController = navController)
                    }

                    composable(
                        route = "${Routes.LABS}/{subjectId}",
                        arguments = listOf(navArgument("subjectId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val subjectId = backStackEntry.arguments?.getInt("subjectId") ?: 0
                        LabsScreen(db = db, navController = navController, subjectId = subjectId)
                    }

                    composable(
                        route = "${Routes.LAB_DETAILS}/{labId}",
                        arguments = listOf(navArgument("labId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val labId = backStackEntry.arguments?.getInt("labId") ?: 0
                        LabDetailsScreen(db = db, navController = navController, labId = labId)
                    }
                }
            }
        }
    }
}

private suspend fun seedIfNeeded(db: AppDatabase) {
    val subjectCount = db.subjectDao().count()
    if (subjectCount > 0) return

    val economicsId = db.subjectDao().insert(SubjectEntity(name = "Economics")).toInt()
    val isdId = db.subjectDao().insert(SubjectEntity(name = "Information systems design")).toInt()

    db.labDao().insertAll(
        listOf(
            LabEntity(
                subjectId = economicsId,
                title = "Midterm examination work",
                status = LabStatus.IN_PROGRESS.name
            )
        )
    )

    val isdLabs = (1..7).map { i ->
        LabEntity(
            subjectId = isdId,
            title = "Lab $i",
            status = LabStatus.DONE.name
        )
    }
    db.labDao().insertAll(isdLabs)
}
