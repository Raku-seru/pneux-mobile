package com.c22ps208.pneux.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.c22ps208.pneux.preferences.SettingPreferences

class ViewModelFactory(
    private val settingPref: SettingPreferences // Untuk enable dark mode
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            // Tambah jika ada penambahan ViewModel
            modelClass.isAssignableFrom(HospitalViewModel::class.java) -> {
                HospitalViewModel() as T
            }
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(settingPref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}