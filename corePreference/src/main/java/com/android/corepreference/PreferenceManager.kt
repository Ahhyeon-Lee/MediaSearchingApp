package com.android.corepreference

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val PROPERTY_FAVORITE = "PROPERTY_FAVORITE"
    }

    fun put(key: String, value: String) =
        sharedPreferences.edit {
            putString(key, value)
        }

    fun get(key: String, def: String = ""): String =
        sharedPreferences.getString(key, def) ?: def
}