package com.example.xcards.domain.repositories

interface RegistrationRepository {
    fun validateData()
    fun firebaseSignUp()
}