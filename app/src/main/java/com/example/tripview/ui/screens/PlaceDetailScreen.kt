package com.example.tripview.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.tripview.viewmodel.PlaceDetailViewModel
import com.example.tripview.viewmodel.PlaceDetailViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    placeId: Int,
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: PlaceDetailViewModel = viewModel(factory = PlaceDetailViewModelFactory(context))

    val place by viewModel.place.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(placeId) {
        viewModel.fetchPlaceDetails(placeId)
        viewModel.checkIfFavorite(placeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(place?.name ?: "Детали") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                viewModel.toggleFavorite(placeId)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Добавить в избранное"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            place?.let {
                Image(
                    painter = rememberImagePainter(it.image_url),
                    contentDescription = it.name,
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = it.name, style = MaterialTheme.typography.headlineMedium)
                Text(text = it.location, style = MaterialTheme.typography.bodyMedium)
                Text(text = it.description, style = MaterialTheme.typography.bodySmall)
            } ?: run {
                Text("Загрузка...", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
