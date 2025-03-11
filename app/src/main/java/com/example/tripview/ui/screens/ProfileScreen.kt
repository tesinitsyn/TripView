package com.example.tripview.ui.screens

import ExpandableSection
import UserRepository
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tripview.data.storage.UserPreferences
import com.example.tripview.network.PlacesRepository
import com.example.tripview.viewmodel.ProfileViewModel
import com.example.tripview.viewmodel.ProfileViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val placesRepository = remember { PlacesRepository() }
    val userRepository = remember { UserRepository() }

    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(userPreferences, placesRepository, userRepository)
    )

    val userPlaces by viewModel.userPlaces.collectAsState()
    val favoritePlaces by viewModel.favoritePlaces.collectAsState()
    val user by viewModel.user.collectAsState()

    var expandedAdded by remember { mutableStateOf(false) }
    var expandedFavorites by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchUserPlaces()
        viewModel.fetchFavoritePlaces()
        viewModel.fetchUserInfo()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Настройки")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            user?.let {
                Text(text = "Привет, ${it.username}!", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
            }

            ExpandableSection(
                title = "Добавленные места",
                expanded = expandedAdded,
                onExpandToggle = { expandedAdded = !expandedAdded }
            ) {
                LazyColumn {
                    items(userPlaces) { place ->
                        PlaceItem(place, onPlaceClick = { navController.navigate("place_detail/${place.id}") })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            ExpandableSection(
                title = "Избранные места",
                expanded = expandedFavorites,
                onExpandToggle = { expandedFavorites = !expandedFavorites }
            ) {
                LazyColumn {
                    items(favoritePlaces) { place ->
                        PlaceItem(place, onPlaceClick  = { navController.navigate("place_detail/${place.id}") })
                    }
                }
            }
        }
    }
}
