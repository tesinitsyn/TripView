package com.example.tripview.network

import com.example.tripview.data.models.AuthResponse
import com.example.tripview.data.models.Place
import com.example.tripview.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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
    suspend fun addPlace(
        @Body place: Place,
        @Header("Authorization") token: String // <-- Передаём токен
    ): Place

    @GET("/places/search/")
    suspend fun searchPlaces(@Query("query") query: String): List<Place>

    @GET("/users/places/")
    suspend fun getUserPlaces(@Header("Authorization") token: String): List<Place>

    @GET("/favorites/")
    suspend fun getFavoritePlaces(@Header("Authorization") token: String): List<Place>

    @GET("/places/{place_id}")
    suspend fun getPlaceById(@Path("place_id") placeId: Int): Place

    @POST("/favorites/")
    suspend fun addToFavorites(
        @Query("place_id") placeId: Int,
        @Header("Authorization") token: String
    ): Response<Unit>

    @DELETE("/favorites/")
    suspend fun removeFromFavorites(
        @Query("place_id") placeId: Int,
        @Header("Authorization") token: String
    ): Response<Unit>

    @GET("/users/me")
    suspend fun getUserInfo(@Header("Authorization") token: String): User

}
