package com.example.tripview.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripview.data.models.Place
import com.example.tripview.data.storage.UserPreferences
import com.example.tripview.network.PlacesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddPlaceViewModel(context: Context) : ViewModel() {
    private val repository = PlacesRepository()
    private val userPreferences = UserPreferences(context)

    fun addPlace(
        name: String,
        description: String,
        location: String,
        imageUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val token = userPreferences.authToken.first() ?: return@launch
                val newPlace = Place(
                    id = 0,
                    name = name,
                    description = description,
                    location = location,
                    image_url = imageUrl
                )
                repository.addPlace(newPlace, token)
                onSuccess()
            } catch (e: Exception) {
                onError("Ошибка: ${e.message}")
            }
        }
    }
}
