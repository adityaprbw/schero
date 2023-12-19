package com.capstone.schero.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.schero.data.datastore.SettingPreferences
import java.lang.IllegalArgumentException

class ThemeViewModelFactory (private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(ThemeViewModel::class.java) -> {
                ThemeViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}