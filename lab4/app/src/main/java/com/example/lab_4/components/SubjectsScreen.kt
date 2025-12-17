package com.example.lab_4.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab_4.data.AppDatabase
import com.example.lab_4.navigation.Routes

@Composable
fun SubjectsScreen(
    db: AppDatabase,
    navController: NavController
) {
    val subjects by db.subjectDao().getAll().collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(
                text = "Subjects",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
            )
        }

        items(subjects, key = { it.id }) { subject ->
            Card(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .clickable {
                        navController.navigate("${Routes.LABS}/${subject.id}")
                    }
            ) {
                Text(
                    text = subject.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
