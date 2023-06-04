package com.example.xcards.presentation.UI.fragments

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.xcards.databinding.ActivityOnboardingBinding
import com.example.xcards.presentation.UI.activities.AuthorizationActivity
import com.example.xcards.presentation.UI.activities.RegistrationActivity
import com.example.xcards.presentation.adapters.OnboardingPagerAdapter

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onboardingPagerAdapter: OnboardingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onboardingPagerAdapter = OnboardingPagerAdapter(supportFragmentManager, lifecycle)

        binding.ViewPager.adapter = onboardingPagerAdapter

        binding.buttonStartTraining.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }

        binding.buttonForOwnersOfAccounts.setOnClickListener {
            startActivity(Intent(this, AuthorizationActivity::class.java))
            finish()
        }
    }
}
