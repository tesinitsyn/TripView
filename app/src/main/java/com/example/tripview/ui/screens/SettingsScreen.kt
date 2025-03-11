package com.example.tripview.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tripview.data.storage.UserPreferences
import com.example.tripview.viewmodel.SettingsViewModel
import com.example.tripview.viewmodel.SettingsViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val viewModel: SettingsViewModel = viewModel(factory = SettingsViewModelFactory(userPreferences))

    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки") },
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
            Text(text = "Настройки", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Переключение темы
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Тёмная тема", style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = { viewModel.toggleTheme() }) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.NightsStay else Icons.Default.WbSunny,
                        contentDescription = "Смена темы"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка выхода
            Button(
                onClick = {
                    viewModel.logout()
                    Toast.makeText(context, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Выйти из аккаунта")
            }
        }
    }
}
