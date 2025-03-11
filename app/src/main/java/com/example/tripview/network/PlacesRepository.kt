package com.example.tripview.network

import com.example.tripview.data.models.Place
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlacesRepository {
    private val api: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/") // Для эмулятора Android Studio
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)
    }

    suspend fun getPlaces(): List<Place> {
        return api.getPlaces()
    }

    suspend fun getPlace(placeId: Int): Place {
        return api.getPlace(placeId)
    }

    suspend fun addPlace(place: Place): Place {  // Добавляем метод
        return api.addPlace(place)
    }
}
