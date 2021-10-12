package com.example.mygithubuser2.viewmodel

import androidx.lifecycle.*
import com.example.mygithubuser2.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SettingPreferences): ViewModel() {
    fun getLanguageSetting(): LiveData<String>{
        return pref.getLanguageSetting().asLiveData()
    }

    fun saveLanguageSetting(languagePreference: String){
        viewModelScope.launch {
            pref.saveLanguageSetting(languagePreference)
        }
    }

    fun getThemeSettings(): LiveData<String> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(themePreference: String) {
        viewModelScope.launch {
            pref.saveThemeSetting(themePreference)
        }
    }
}