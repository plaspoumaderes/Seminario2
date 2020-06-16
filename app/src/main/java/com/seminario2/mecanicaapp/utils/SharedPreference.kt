package com.seminario2.mecanicaapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {
    private val PREFS_NAME = "kotlincodes"
    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit().apply {
            this.putString(KEY_NAME, text)
            this.commit()
        }
    }

    fun save(KEY_NAME: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPref.edit().apply {
            this.putInt(KEY_NAME, value)
            this.commit()
        }
    }

    fun save(KEY_NAME: String, status: Boolean = false) {
        val editor: SharedPreferences.Editor = sharedPref.edit().apply {
            this.putBoolean(KEY_NAME, status)
            this.commit()
        }
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueInt(KEY_NAME: String): Int {
        return sharedPref.getInt(KEY_NAME, 0)
    }

    fun getValueBoolien(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

}