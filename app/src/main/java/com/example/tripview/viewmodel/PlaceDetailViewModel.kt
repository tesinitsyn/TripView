package com.example.tripview.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripview.data.models.Place
import com.example.tripview.data.storage.UserPreferences
import com.example.tripview.network.PlacesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PlaceDetailViewModel(context: Context) : ViewModel() {
    private val repository = PlacesRepository()
    private val userPreferences = UserPreferences(context)

    private val _place = MutableStateFlow<Place?>(null)
    val place: StateFlow<Place?> = _place

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun fetchPlaceDetails(placeId: Int) {
        viewModelScope.launch {
            try {
                _place.value = repository.getPlaceById(placeId)
            } catch (e: Exception) {
                _place.value = null
            }
        }
    }

    fun checkIfFavorite(placeId: Int) {
        viewModelScope.launch {
            val token = userPreferences.authToken.first() ?: return@launch
            _isFavorite.value = repository.isFavorite(placeId, token)
        }
    }

    fun toggleFavorite(placeId: Int) {
        viewModelScope.launch {
            val token = userPreferences.authToken.first() ?: return@launch
            if (_isFavorite.value) {
                repository.removeFromFavorites(placeId, token)
            } else {
                repository.addToFavorites(placeId, token)
            }
            _isFavorite.value = !_isFavorite.value
        }
    }
}
