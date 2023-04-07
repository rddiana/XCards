package com.example.xcards.presentation.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.xcards.R
import com.example.xcards.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        binding.materialButtonNext.setOnClickListener {
            login()
        }

        binding.materialButtonForBeginners.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }
    }

    private fun login() {
        val email = binding.emailAddressEditText.text.toString()
        val pass = binding.editTextPassword.text.toString()

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                startActivity(Intent(this, MainActivity::class.java))
            } else
                binding.textViewForMessage1.text = getString(R.string.authorization_problem)
        }
    }
}