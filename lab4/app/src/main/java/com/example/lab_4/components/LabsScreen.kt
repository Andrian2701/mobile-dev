package com.example.lab_4.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab_4.data.AppDatabase
import com.example.lab_4.data.LabStatus
import com.example.lab_4.navigation.Routes
import com.example.lab_4.ui.components.BackTopBar

@Composable
fun LabsScreen(
    db: AppDatabase,
    navController: NavController,
    subjectId: Int
) {
    val subject by db.subjectDao().getById(subjectId).collectAsState(initial = null)
    val labs by db.labDao().getBySubject(subjectId).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            BackTopBar(
                title = subject?.name ?: "Labs",
                onBack = { navController.popBackStack() }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(labs, key = { it.id }) { lab ->
                val status = LabStatus.fromDb(lab.status)

                Card(
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .clickable { navController.navigate("${Routes.LAB_DETAILS}/${lab.id}") }
                ) {
                    Text(
                        text = lab.title,
                        modifier = Modifier.padding(start = 16.dp, top = 14.dp, end = 16.dp)
                    )
                    Text(
                        text = "Status: ${status.label}",
                        modifier = Modifier.padding(start = 16.dp, bottom = 14.dp, end = 16.dp)
                    )
                }
            }
        }
    }
}
