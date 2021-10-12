package com.example.mygithubuser2.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.mygithubuser2.R
import com.example.mygithubuser2.SettingPreferences
import com.example.mygithubuser2.databinding.ActivitySettingsBinding
import com.example.mygithubuser2.helper.ViewModelFactorySettings
import com.example.mygithubuser2.viewmodel.SettingViewModel
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity(), LanguageDialogFragment.OnOptionDialogListener, ThemeDialogFragment.OnThemeDialogListener, View.OnClickListener {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.settings_activity)

        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactorySettings(pref))
            .get(SettingViewModel::class.java)

        binding.languageSetting.setOnClickListener(this)
        binding.themeSetting.setOnClickListener(this)

        settingViewModel.getThemeSettings().observe(this,
            { themePreference: String ->
                if (themePreference == "dark") {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    binding.themeIndicator.text = getString(R.string.dark)
                } else if (themePreference == "light"){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    binding.themeIndicator.text = getString(R.string.light)
                }
            })
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.language_setting -> {
                val mLanguageDialogFragment = LanguageDialogFragment()
                val mFragmentManager = supportFragmentManager

                mLanguageDialogFragment.show(mFragmentManager, LanguageDialogFragment::class.java.simpleName)
            }

            R.id.theme_setting -> {
                val mThemeDialogFragment = ThemeDialogFragment()
                val mFragmentManager = supportFragmentManager

                mThemeDialogFragment.show(mFragmentManager, ThemeDialogFragment::class.java.simpleName)
            }
        }
    }

    override fun onOptionChosen(text: String?) {
        if (text != null) {
            settingViewModel.saveLanguageSetting(text)
            val restart = Intent(this@SettingsActivity, SplashScreen::class.java)
            startActivity(restart)
            finish()
        }
    }

    override fun onThemeChosen(text: String?) {
        if (text != null) {
            settingViewModel.saveThemeSetting(text)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                finish()
                true
            }

            else -> true
        }
    }
}