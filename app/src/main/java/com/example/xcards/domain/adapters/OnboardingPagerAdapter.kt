package com.example.xcards.domain.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.xcards.R

class OnboardingPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(
        fragmentManager,
        lifecycle
    ) {
    private val fragmentArray: Array<Fragment>
        get() = TODO()

    init {
        fragmentArray.plus(Fragment(R.layout.fragment_onboarding_first_page))
        fragmentArray.plus(Fragment(R.layout.fragment_onboarding_second_page))
        fragmentArray.plus(Fragment(R.layout.fragment_onboarding_third_page))
        fragmentArray.plus(Fragment(R.layout.fragment_onboarding_forth_page))
    }

    override fun getItemCount(): Int {
        return fragmentArray.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentArray[position]
    }
}

