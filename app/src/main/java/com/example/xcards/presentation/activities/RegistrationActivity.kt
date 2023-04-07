package com.example.xcards.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xcards.R
import com.example.xcards.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.materialButtonNext.setOnClickListener {
            signUp()
        }

        binding.materialButtonForOwnersOfAccounts.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun signUp() {
        val name = binding.editTextTextPersonName.text.toString() //в DataStore для дальнейшего использования
        val email = binding.editTextTextEmailAddress.text.toString()
        val password = binding.editTextNewPassword.text.toString()
        val confirmedPassword = binding.editTextConfirmedPassword.text.toString()

        if (email.isBlank() || password.isBlank() || confirmedPassword.isBlank()) {
            binding.textViewForMessage2.text = getString(R.string.invalid_data)
            return
        }

        if (password != confirmedPassword) {
            binding.textViewForMessage2.text = getString(R.string.no_matching_passwords)
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                binding.textViewForMessage2.text = getString(R.string.server_failed)
            }
        }
    }
}