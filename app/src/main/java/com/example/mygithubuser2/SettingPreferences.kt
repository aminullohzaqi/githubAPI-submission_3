package com.example.mygithubuser2

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){
    private val LANGUAGE_KEY = stringPreferencesKey("language_setting")
    private val THEME_KEY = stringPreferencesKey("theme_setting")

    fun getLanguageSetting(): Flow<String> {
        return dataStore.data.map { language ->
            language[LANGUAGE_KEY] ?: "en"
        }
    }

    suspend fun saveLanguageSetting(languagePreference: String){
        dataStore.edit { language ->
            language[LANGUAGE_KEY] = languagePreference
        }
    }

    fun getThemeSetting(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: "light"
        }
    }

    suspend fun saveThemeSetting(themePreference: String) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = themePreference
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}