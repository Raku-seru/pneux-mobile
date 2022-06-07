package com.c22ps208.pneux.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.c22ps208.pneux.preferences.SettingPreferences
import kotlinx.coroutines.launch

class NewsViewModel(
    private val settingPref: SettingPreferences
) : ViewModel() {

    // Theme
    fun getThemeSetting(): LiveData<Boolean> {
        return settingPref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingPref.saveThemeSetting(isDarkModeActive)
        }
    }

    init {
        Log.i(TAG, "NewsViewModel Created")
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}