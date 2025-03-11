package com.example.tripview.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddPlaceViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPlaceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddPlaceViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
