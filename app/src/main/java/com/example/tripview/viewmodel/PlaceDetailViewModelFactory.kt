package com.example.tripview.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlaceDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaceDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaceDetailViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
