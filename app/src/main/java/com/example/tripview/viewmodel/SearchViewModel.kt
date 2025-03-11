package com.example.tripview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripview.data.models.Place
import com.example.tripview.network.PlacesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository = PlacesRepository()

    private val _searchResults = MutableStateFlow<List<Place>>(emptyList())
    val searchResults: StateFlow<List<Place>> = _searchResults

    fun searchPlaces(query: String) {
        viewModelScope.launch {
            try {
                _searchResults.value = repository.searchPlaces(query)
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            }
        }
    }
}
