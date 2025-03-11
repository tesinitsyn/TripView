import com.example.tripview.data.models.User
import com.example.tripview.data.storage.UserPreferences
import com.example.tripview.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository {
    private val api: ApiService

    init {
        val retrofit = Retrofit.Builder().baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        api = retrofit.create(ApiService::class.java)
    }

    suspend fun getUserInfo(token: String): User {
        return api.getUserInfo("Bearer $token")
    }
}
