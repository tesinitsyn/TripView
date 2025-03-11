package com.example.tripview.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.tripview.viewmodel.PlaceDetailViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(placeId: Int, navController: NavController, viewModel: PlaceDetailViewModel = viewModel()) {
    val place by viewModel.place.collectAsState()

    LaunchedEffect(placeId) {
        viewModel.fetchPlace(placeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(place?.name ?: "Детали места") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (place == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(place!!.image_url),
                    contentDescription = place!!.name,
                    modifier = Modifier.fillMaxWidth().height(300.dp)
                )
                Text(text = place!!.name, style = MaterialTheme.typography.headlineLarge)
                Text(text = place!!.location, style = MaterialTheme.typography.bodyMedium)
                Text(text = place!!.description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
