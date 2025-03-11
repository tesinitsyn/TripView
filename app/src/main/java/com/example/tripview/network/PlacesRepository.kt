package com.example.tripview.network

import com.example.tripview.data.models.Place
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlacesRepository {
    private val api: ApiService

    init {
        val retrofit =
            Retrofit.Builder().baseUrl("http://10.0.2.2:8000/") // Для эмулятора Android Studio
                .addConverterFactory(GsonConverterFactory.create()).build()

        api = retrofit.create(ApiService::class.java)
    }

    suspend fun getPlaces(): List<Place> {
        return api.getPlaces()
    }

    suspend fun getPlaceById(placeId: Int): Place {
        return api.getPlaceById(placeId)
    }

    suspend fun getUserPlaces(token: String): List<Place> {
        return api.getUserPlaces("Bearer $token")
    }

    suspend fun getFavoritePlaces(token: String): List<Place> {
        return api.getFavoritePlaces("Bearer $token")
    }

    suspend fun addToFavorites(placeId: Int, token: String): Boolean {
        return try {
            val response = api.addToFavorites(placeId, "Bearer $token")
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun removeFromFavorites(placeId: Int, token: String): Boolean {
        return try {
            val response = api.removeFromFavorites(placeId, "Bearer $token")
            response.isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun isFavorite(placeId: Int, token: String): Boolean {
        return try {
            val favorites = api.getFavoritePlaces("Bearer $token")
            favorites.any { it.id == placeId }
        } catch (e: Exception) {
            false
        }
    }

    suspend fun addPlace(place: Place, token: String): Boolean {
        return try {
            val response = api.addPlace(place, "Bearer $token") // <-- Указываем токен
            response.id > 0 // Проверяем, что место успешно добавлено
        } catch (e: Exception) {
            false
        }
    }

    suspend fun searchPlaces(query: String): List<Place> {
        return api.searchPlaces(query)
    }

}
