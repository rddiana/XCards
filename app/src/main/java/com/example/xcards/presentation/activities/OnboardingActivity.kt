package com.example.xcards.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.xcards.data.Constants
import com.example.xcards.databinding.ActivityOnboardingBinding
import com.example.xcards.domain.adapters.OnboardingPagerAdapter

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onboardingPagerAdapter: OnboardingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onboardingPagerAdapter = OnboardingPagerAdapter(supportFragmentManager, lifecycle)

        binding.ViewPager.adapter = onboardingPagerAdapter

        getSharedPreferences("preferences", MODE_PRIVATE)
            .edit()
            .putBoolean(
                Constants().isOnboardingPassedKey,
                true
            )
            .apply()

        finish()
    }
}
