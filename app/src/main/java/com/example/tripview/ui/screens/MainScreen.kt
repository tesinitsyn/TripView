package com.example.tripview.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tripview.viewmodel.PlacesViewModel
import com.example.tripview.data.models.Place

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,  // Передаём navController
    placesViewModel: PlacesViewModel = viewModel(),
    onPlaceClick: (Int) -> Unit
) {
    val places by placesViewModel.places.collectAsState()

    LaunchedEffect(Unit) {
        placesViewModel.fetchPlaces()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trip View") },
                actions = {
                    // Кнопка "Поиск"
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(Icons.Default.Search, contentDescription = "Поиск")
                    }

                    // Кнопка "Профиль"
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.Person, contentDescription = "Профиль")
                    }
                    // Кнопка "Добавить место"
                    IconButton(onClick = { navController.navigate("add_place") }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить место")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (places.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                items(places) { place ->
                    PlaceItem(place, onPlaceClick)
                }
            }
        }
    }
}

@Composable
fun PlaceItem(place: Place, onPlaceClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onPlaceClick(place.id) }, // Открываем детали при нажатии
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(place.image_url),
                contentDescription = place.name,
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )
            Text(text = place.name, style = MaterialTheme.typography.headlineSmall)
            Text(text = place.location, style = MaterialTheme.typography.bodyMedium)
            Text(text = place.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}
