package com.example.xcards.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.xcards.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        checkUser()

        binding.button.setOnClickListener{
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }

//        binding.materialCardView7.setOnClickListener {
//            binding.materialCardView7.setCardBackgroundColor(getColor(R.color.light_blue))
//        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }
    }
}