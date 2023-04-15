package com.example.xcards.data

import com.google.firebase.auth.EmailAuthProvider

data class UserData(
    val fullName: String? = null,
    val email: EmailAuthProvider? = null,
    val password: String? = null
)
