package com.example.tripview.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripview.data.models.User
import com.example.tripview.network.AuthRepository
import com.example.tripview.data.storage.UserPreferences
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository()
    private val userPreferences = UserPreferences(application)

    fun register(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.register(User(username, password))
                userPreferences.saveAuthToken(response.access_token)  // Сохраняем токен
                onSuccess()
            } catch (e: Exception) {
                onError("Ошибка регистрации: ${e.message}")
            }
        }
    }

    fun login(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.login(User(username, password))
                userPreferences.saveAuthToken(response.access_token)  // Сохраняем токен
                onSuccess()
            } catch (e: Exception) {
                onError("Ошибка входа: ${e.message}")
            }
        }
    }
}
