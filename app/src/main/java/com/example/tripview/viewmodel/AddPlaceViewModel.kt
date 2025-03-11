package com.example.tripview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripview.data.models.Place
import com.example.tripview.network.PlacesRepository
import kotlinx.coroutines.launch

class AddPlaceViewModel : ViewModel() {
    private val repository = PlacesRepository()

    fun addPlace(name: String, description: String, location: String, imageUrl: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val newPlace = Place(id = 0, name = name, description = description, location = location, image_url = imageUrl)
                repository.addPlace(newPlace)
                onSuccess()
            } catch (e: Exception) {
                onError("Ошибка: ${e.message}")
            }
        }
    }
}
