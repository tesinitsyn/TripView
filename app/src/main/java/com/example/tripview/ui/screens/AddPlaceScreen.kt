package com.example.tripview.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tripview.viewmodel.AddPlaceViewModel
import com.example.tripview.viewmodel.AddPlaceViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: AddPlaceViewModel = viewModel(factory = AddPlaceViewModelFactory(context))

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить место") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
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
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Название") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Описание") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Локация") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Ссылка на изображение") },
                modifier = Modifier.fillMaxWidth()
            )
            errorMessage?.let { Text(it, color = Color.Red) }

            Button(
                onClick = {
                    viewModel.addPlace(
                        name, description, location, imageUrl,
                        onSuccess = { navController.popBackStack() },
                        onError = { errorMessage = it }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Добавить")
            }
        }
    }
}
