package com.example.tripview.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tripview.data.storage.UserPreferences
import com.example.tripview.ui.screens.*

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
}

@Composable
fun AppNavGraph(startDestination: String = Screen.Login.route, onThemeChanged: () -> Unit) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }

    // Запоминаем стартовый экран и поисковый запрос
    var startDestinationState by rememberSaveable { mutableStateOf(startDestination) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(navController) {
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
                navController = navController,
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
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("search") {
            SearchScreen(navController, searchQuery, onQueryChange = { searchQuery = it })
        }
        composable("settings") {
            SettingsScreen(navController, onThemeChanged)
        }
    }
}
