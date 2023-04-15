package com.example.xcards.domain.repositories

interface AuthorizationRepository {
    fun validateData()
    fun firebaseLogin()
}