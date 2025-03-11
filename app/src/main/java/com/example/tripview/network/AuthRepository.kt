package com.example.tripview.network

import com.example.tripview.data.models.AuthResponse
import com.example.tripview.data.models.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthRepository {
    private val api: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/") // Для эмулятора Android Studio
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)
    }

    suspend fun register(user: User): AuthResponse {
        return api.register(user)
    }

    suspend fun login(user: User): AuthResponse {
        return api.login(user)
    }
}
