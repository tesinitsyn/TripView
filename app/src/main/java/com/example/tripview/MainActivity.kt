package com.example.tripview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.tripview.data.storage.UserPreferences
import com.example.tripview.ui.navigation.AppNavGraph
import com.example.tripview.ui.theme.TripViewTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userPreferences = UserPreferences(this)
        val isDarkTheme = runBlocking { userPreferences.isDarkTheme.first() }

        setContent {
            var darkTheme by remember { mutableStateOf(isDarkTheme) }

            TripViewTheme(darkTheme = darkTheme) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavGraph(
                        onThemeChanged = { darkTheme = !darkTheme }
                    )
                }
            }
        }
    }
}
