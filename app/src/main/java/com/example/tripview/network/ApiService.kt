package com.example.tripview.network

import com.example.tripview.data.models.AuthResponse
import com.example.tripview.data.models.Place
import com.example.tripview.data.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/users/register")
    suspend fun register(@Body user: User): AuthResponse

    @POST("/users/token")
    suspend fun login(@Body user: User): AuthResponse

    @GET("/places/")
    suspend fun getPlaces(): List<Place>

    @GET("/places/{placeId}")
    suspend fun getPlace(@Path("placeId") placeId: Int): Place

    @POST("/places/")
    suspend fun addPlace(@Body place: Place): Place


}
