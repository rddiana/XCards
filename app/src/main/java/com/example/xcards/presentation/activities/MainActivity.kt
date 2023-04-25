package com.example.xcards.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.xcards.R
import com.example.xcards.databinding.ActivityMainBinding
import com.example.xcards.presentation.ChartFragment
import com.example.xcards.presentation.ProfileFragment
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        checkUser()

        fragmentManager = supportFragmentManager

        binding.toChartFragment.setOnClickListener {
            changeFragmentWithButtonNav(binding.toChartFragment)
        }

        binding.toHomeFragment.setOnClickListener {

        }

        binding.toProfileFragment.setOnClickListener {
            changeFragmentWithButtonNav(binding.toProfileFragment)
        }
    }

    private fun changeFragmentWithButtonNav(cardView: MaterialCardView) {
        val fragment = ProfileFragment()
        fragmentManager.beginTransaction().replace(R.id.main_activity, fragment).commit()

        if (cardView.cardBackgroundColor.equals(resources.getColor(R.color.light_blue))) {
            cardView.setBackgroundColor(resources.getColor(R.color.transparent))
        } else {
            cardView.setBackgroundColor(resources.getColor(R.color.light_blue))
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }
    }
}