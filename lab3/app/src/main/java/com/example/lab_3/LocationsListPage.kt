package com.example.lab_3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class CityLocation(val name: String, val description: String)

@Composable
fun LocationsListPage(navController: NavController) {
    val locations = listOf(
        CityLocation("Central Park", "Beautiful park in the city center with lakes and trails."),
        CityLocation("Art Museum", "Large collection of modern and classical art."),
        CityLocation("Old Town Square", "Historic area with cafes and street musicians."),
        CityLocation("Science Center", "Interactive exhibits and planetarium shows."),
        CityLocation("River Walk", "Peaceful walkway along the main river with sunset views.")
    )

    Scaffold() { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(locations.size) { index ->
                val location = locations[index]
                LocationItem(location) {
                    navController.navigate(
                        Screen.LocationDetails.createRoute(
                            location.name,
                            location.description
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun LocationItem(location: CityLocation, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = location.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = location.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
