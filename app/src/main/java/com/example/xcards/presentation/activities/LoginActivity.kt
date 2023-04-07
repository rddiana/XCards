package com.example.xcards.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xcards.R
import com.example.xcards.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.materialButtonNext.setOnClickListener {
            signUp()
        }

        binding.materialButtonForBeginners.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    private fun signUp() {
        val email = binding.emailAddressEditText.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email.isBlank() || password.isBlank()) {
            binding.textViewForMessage1.text = getString(R.string.invalid_data)
            return
        }
    }
}