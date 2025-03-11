package com.example.tripview.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripview.data.storage.UserPreferences
import com.example.tripview.ui.screens.AddPlaceScreen
import com.example.tripview.ui.screens.LoginScreen
import com.example.tripview.ui.screens.RegisterScreen
import com.example.tripview.ui.screens.MainScreen
import com.example.tripview.ui.screens.PlaceDetailScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
}

@Composable
fun AppNavGraph(startDestination: String = Screen.Login.route) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    var startDestinationState by remember { mutableStateOf(startDestination) }

    LaunchedEffect(Unit) {
        userPreferences.authToken.collect { token ->
            if (!token.isNullOrEmpty()) {
                startDestinationState = Screen.Main.route
            }
        }
    }

    NavHost(navController = navController, startDestination = startDestinationState) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Main.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Screen.Main.route) },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Screen.Main.route) {
            MainScreen(
                navController = navController,  // Передаём navController
                onPlaceClick = { placeId -> navController.navigate("place_detail/$placeId") }
            )
        }

        composable("place_detail/{placeId}") { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")?.toIntOrNull()
            if (placeId != null) {
                PlaceDetailScreen(placeId, navController)
            }
        }
        composable("add_place") {
            AddPlaceScreen(navController)
        }
    }
}



