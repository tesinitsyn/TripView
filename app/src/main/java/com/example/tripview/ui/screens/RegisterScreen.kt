package com.example.tripview.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tripview.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel = viewModel(),
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Регистрация", style = MaterialTheme.typography.headlineLarge)

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Логин") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        errorMessage?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        Button(
            onClick = {
                authViewModel.register(username, password,
                    onSuccess = { onRegisterSuccess() },
                    onError = { errorMessage = it })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Зарегистрироваться")
        }

        TextButton(onClick = onNavigateToLogin) {
            Text("Уже есть аккаунт? Войти")
        }
    }
}

