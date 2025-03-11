package com.example.tripview.viewmodel

import UserRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripview.data.models.Place
import com.example.tripview.data.models.User
import com.example.tripview.data.storage.UserPreferences
import com.example.tripview.network.PlacesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userPreferences: UserPreferences,
    private val repository: PlacesRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _userPlaces = MutableStateFlow<List<Place>>(emptyList())
    val userPlaces: StateFlow<List<Place>> = _userPlaces

    private val _favoritePlaces = MutableStateFlow<List<Place>>(emptyList())
    val favoritePlaces: StateFlow<List<Place>> = _favoritePlaces

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    fun fetchUserPlaces() {
        viewModelScope.launch {
            val token = userPreferences.authToken.first() ?: return@launch
            try {
                _userPlaces.value = repository.getUserPlaces(token)
            } catch (e: Exception) {
                _userPlaces.value = emptyList()
            }
        }
    }

    fun fetchUserInfo() {
        viewModelScope.launch {
            val token = userPreferences.authToken.first() ?: return@launch
            try {
                _user.value = userRepository.getUserInfo(token)
            } catch (e: Exception) {
                _user.value = null
            }
        }
    }


    fun fetchFavoritePlaces() {
        viewModelScope.launch {
            val token = userPreferences.authToken.first() ?: return@launch
            try {
                _favoritePlaces.value = repository.getFavoritePlaces(token)
            } catch (e: Exception) {
                _favoritePlaces.value = emptyList()
            }
        }
    }
}

