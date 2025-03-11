package com.example.tripview.data.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val IS_DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme") // Ключ для темы
    }

    // Сохранение токена
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    // Получение токена
    val authToken: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[TOKEN_KEY] }

    // Удаление токена (выход из аккаунта)
    suspend fun clearAuthToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    // Сохранение темы (true - тёмная, false - светлая)
    suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_THEME_KEY] = isDark
        }
    }

    // Получение текущей темы
    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { preferences -> preferences[IS_DARK_THEME_KEY] ?: false }
}
