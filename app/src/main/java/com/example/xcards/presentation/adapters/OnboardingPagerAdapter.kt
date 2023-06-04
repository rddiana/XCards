package com.example.xcards.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.xcards.R

class OnboardingPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentsArray = arrayOf(
        Fragment(R.layout.fragment_onboarding_first_page),
        Fragment(R.layout.fragment_onboarding_second_page),
        Fragment(R.layout.fragment_onboarding_third_page),
        Fragment(R.layout.fragment_onboarding_forth_page)
    )

    override fun createFragment(position: Int): Fragment {
        return fragmentsArray[position]
    }

    override fun getItemCount(): Int {
        return fragmentsArray.size
    }
}