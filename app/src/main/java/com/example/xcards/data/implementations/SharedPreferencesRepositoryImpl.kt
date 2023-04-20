package com.example.xcards.data.implementations

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.example.xcards.data.Constants
import com.example.xcards.domain.repositories.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(context: Context) : SharedPreferencesRepository {
    private val sharedPreferences: SharedPreferences
    private val sharedPreferencesEditor: Editor

    init {
        sharedPreferences = context.getSharedPreferences(
            Constants.PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        sharedPreferencesEditor = sharedPreferences.edit()
    }

    override fun saveString(key: String?, value: String?) {
        sharedPreferencesEditor.putString(key, value).commit()
    }

    override fun loadString(key: String?): String? {
        return sharedPreferences.getString(key, "")
    }
}