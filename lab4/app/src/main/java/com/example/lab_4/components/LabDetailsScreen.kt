package com.example.lab_4.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab_4.data.AppDatabase
import com.example.lab_4.data.CommentEntity
import com.example.lab_4.data.LabStatus
import com.example.lab_4.ui.components.BackTopBar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LabDetailsScreen(
    db: AppDatabase,
    navController: NavController,
    labId: Int
) {
    val scope = rememberCoroutineScope()

    val lab by db.labDao().getById(labId).collectAsState(initial = null)
    val comments by db.commentDao().getByLab(labId).collectAsState(initial = emptyList())

    var statusMenuExpanded by remember { mutableStateOf(false) }
    var commentInput by remember { mutableStateOf("") }

    LaunchedEffect(labId) { }

    val title = lab?.title ?: "Lab"
    val currentStatus = LabStatus.fromDb(lab?.status ?: LabStatus.IN_PROGRESS.name)

    Scaffold(
        topBar = {
            BackTopBar(
                title = title,
                onBack = { navController.popBackStack() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Status: ${currentStatus.label}",
                style = MaterialTheme.typography.titleMedium
            )

            Button(onClick = { statusMenuExpanded = true }) {
                Text("Change status")
            }
            DropdownMenu(
                expanded = statusMenuExpanded,
                onDismissRequest = { statusMenuExpanded = false }
            ) {
                LabStatus.entries.forEach { s ->
                    DropdownMenuItem(
                        text = { Text(s.label) },
                        onClick = {
                            statusMenuExpanded = false
                            scope.launch {
                                db.labDao().updateStatus(labId, s.name)
                            }
                        }
                    )
                }
            }

            OutlinedTextField(
                value = commentInput,
                onValueChange = { commentInput = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Add comment") }
            )

            Button(
                onClick = {
                    val text = commentInput.trim()
                    if (text.isNotEmpty()) {
                        scope.launch {
                            db.commentDao().insert(
                                CommentEntity(labId = labId, text = text)
                            )
                        }
                        commentInput = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save comment")
            }

            Text(
                text = "Comments",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(comments, key = { it.id }) { c ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = formatTime(c.createdAt),
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(start = 12.dp, top = 10.dp, end = 12.dp)
                        )
                        Text(
                            text = c.text,
                            modifier = Modifier.padding(start = 12.dp, bottom = 12.dp, end = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun formatTime(millis: Long): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(millis))
}
