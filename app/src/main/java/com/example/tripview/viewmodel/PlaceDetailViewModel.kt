package com.example.tripview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripview.data.models.Place
import com.example.tripview.network.PlacesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaceDetailViewModel : ViewModel() {
    private val repository = PlacesRepository()

    private val _place = MutableStateFlow<Place?>(null)
    val place: StateFlow<Place?> = _place

    fun fetchPlace(placeId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getPlace(placeId)
                _place.value = response
            } catch (e: Exception) {
                _place.value = null
            }
        }
    }
}
