package com.example.xcards.domain.useCase

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPreference(val context: Context) {
    private val PREFS_NAME = "sharedPref"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun save(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor.commit()
    }

//    fun save(KEY_NAME: String, value: Int) {
//        val editor: SharedPreferences.Editor = sharedPref.edit()
//        editor.putInt(KEY_NAME, value)
//        editor.commit()
//    }

    fun save(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, status!!)
        editor.commit()
    }

    fun save(KEY_NAME: String, value: Float) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putFloat(KEY_NAME, value)
        editor.commit()
    }

    fun getValueString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun getValueFloat(KEY_NAME: String): Float {
        return sharedPref.getFloat(KEY_NAME, 0f)
    }

//    fun getValueInt(KEY_NAME: String): Int {
//        return sharedPref.getInt(KEY_NAME, 0)
//    }

    fun getValueBoolean(KEY_NAME: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(KEY_NAME, defaultValue)
    }

    fun updateStringValue(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.putString(KEY_NAME, text)
        editor.commit()
    }

    fun updateFloatValue(KEY_NAME: String, value: Float) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.putFloat(KEY_NAME, value)
        editor.commit()
    }

//    fun updateIntValue(KEY_NAME: String, value: Int) {
//        val editor: SharedPreferences.Editor = sharedPref.edit()
//        editor.remove(KEY_NAME)
//        editor.putInt(KEY_NAME, value)
//        editor.commit()
//    }

    fun updateBooleanValue(KEY_NAME: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.putBoolean(KEY_NAME, status)
        editor.commit()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.commit()
    }
}
