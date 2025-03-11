package com.example.tripview.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tripview.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }
    val searchResults by searchViewModel.searchResults.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Поиск") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
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
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    searchViewModel.searchPlaces(it)  // Запуск поиска
                },
                label = { Text("Введите запрос") },
                modifier = Modifier.fillMaxWidth()
            )

            if (searchResults.isEmpty() && query.isNotEmpty()) {
                Text("Ничего не найдено", modifier = Modifier.padding(16.dp))
            } else {
                LazyColumn {
                    items(searchResults) { place ->
                        PlaceItem(place) {}
                    }
                }
            }
        }
    }
}
