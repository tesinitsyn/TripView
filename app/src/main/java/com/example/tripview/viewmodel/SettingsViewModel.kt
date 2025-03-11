package com.example.tripview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripview.data.storage.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    init {
        viewModelScope.launch {
            _isDarkTheme.value = userPreferences.isDarkTheme.first()
        }
    }

    fun toggleTheme() {
        viewModelScope.launch {
            val newTheme = !_isDarkTheme.value
            userPreferences.saveTheme(newTheme)
            _isDarkTheme.value = newTheme
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearAuthToken()
        }
    }
}
