package com.example.mygithubuser2.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mygithubuser2.R
import com.example.mygithubuser2.SettingPreferences
import com.example.mygithubuser2.helper.ViewModelFactorySettings
import com.example.mygithubuser2.viewmodel.SettingViewModel
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var locale: Locale

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val prefTheme = SettingPreferences.getInstance(dataStore)

        settingViewModel = ViewModelProvider(this, ViewModelFactorySettings(prefTheme))
            .get(SettingViewModel::class.java)

        settingViewModel.getThemeSettings().observe(this,
            { themePreference: String ->
                if (themePreference == "dark") {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else if (themePreference == "light"){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

        settingViewModel.getLanguageSetting().observe(this,
            { languagePreference: String ->
                locale = Locale(languagePreference)
                val res = resources
                val dm = res.displayMetrics
                val conf = res.configuration
                conf.locale = locale
                res.updateConfiguration(conf, dm)
            })

        supportActionBar?.hide()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}