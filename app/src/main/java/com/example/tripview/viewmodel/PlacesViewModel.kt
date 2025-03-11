package com.example.tripview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripview.data.models.Place
import com.example.tripview.network.PlacesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlacesViewModel : ViewModel() {
    private val repository = PlacesRepository()

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places

    fun fetchPlaces() {
        viewModelScope.launch {
            try {
                val response = repository.getPlaces()
                _places.value = response
            } catch (e: Exception) {
                // Обрабатываем ошибку (например, показываем заглушку)
                _places.value = emptyList()
            }
        }
    }
}
