package com.example.tripview.viewmodel

import UserRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tripview.data.storage.UserPreferences
import com.example.tripview.network.PlacesRepository

class ProfileViewModelFactory(
    private val userPreferences: UserPreferences,
    private val placesRepository: PlacesRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(userPreferences, placesRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


