package com.example.xcards.presentation.activities

import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.xcards.R
import com.example.xcards.databinding.ActivityMainBinding
import com.example.xcards.presentation.ChartFragment
import com.example.xcards.presentation.HomeFragment
import com.example.xcards.presentation.ProfileFragment
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var fragmentManager: FragmentManager

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

//        checkUser()


        fragmentManager = supportFragmentManager
        turnButtonNavOn(binding.toHomeFragment)

        binding.toChartFragment.setOnClickListener {
            changeFragment(binding.toChartFragment)
            turnButtonNavOn(binding.toChartFragment)
            turnButtonNavOff(binding.toHomeFragment)
            turnButtonNavOff(binding.toProfileFragment)
        }

        binding.toHomeFragment.setOnClickListener {
            changeFragment(binding.toHomeFragment)
            turnButtonNavOn(binding.toHomeFragment)
            turnButtonNavOff(binding.toChartFragment)
            turnButtonNavOff(binding.toProfileFragment)
        }

        binding.toProfileFragment.setOnClickListener {
            changeFragment(binding.toProfileFragment)
            turnButtonNavOn(binding.toProfileFragment)
            turnButtonNavOff(binding.toHomeFragment)
            turnButtonNavOff(binding.toChartFragment)
        }
    }

    private fun turnButtonNavOn(cardView: CardView) {
        var cardColor = ContextCompat.getColor(this, R.color.sky_blue)
        cardView.setCardBackgroundColor(cardColor)
    }

    private fun turnButtonNavOff(cardView: CardView) {
        var cardColor = ContextCompat.getColor(this, R.color.transparent)
        cardView.setCardBackgroundColor(cardColor)
    }

    private fun changeFragment(cardView: CardView) {
        var fragment = Fragment()

        when (cardView) {
            binding.toChartFragment -> fragment = ChartFragment()
            binding.toHomeFragment -> fragment = HomeFragment()
            binding.toProfileFragment -> fragment = ProfileFragment()
        }

        fragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, fragment).commit()
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }
    }
}