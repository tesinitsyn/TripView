package com.example.tripview.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, onThemeChanged: () -> Unit) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModelFactory(userPreferences)
    )

    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val coroutineScope = rememberCoroutineScope()

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
            Text("Настройки", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Переключение темы
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Темная тема")
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = {
                        viewModel.toggleTheme()
                        onThemeChanged() // Переключаем тему в `MainActivity`
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Кнопка выхода из аккаунта
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo("main") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Выйти",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Выйти из аккаунта")
            }
        }
    }
}
