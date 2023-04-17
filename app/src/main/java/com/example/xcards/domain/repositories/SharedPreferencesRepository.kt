package com.example.xcards.domain.repositories

interface SharedPreferencesRepository {
    fun saveString(key: String?, value: String?)
    fun loadString(key: String?): String?
}