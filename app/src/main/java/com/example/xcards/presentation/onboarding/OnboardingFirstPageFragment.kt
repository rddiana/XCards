package com.example.xcards.presentation.onboarding

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import androidx.fragment.app.Fragment
import com.example.xcards.R
import com.example.xcards.databinding.FragmentOnboardingFirstPageBinding

class OnboardingFirstPageFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingFirstPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingFirstPageBinding.inflate(layoutInflater)

//        val animation = TranslateAnimation(20f, (binding.root.width / 2).toFloat(), 0f, 0f)
//        animation.duration = 1000
//        animation.repeatCount = 5
//        animation.repeatMode = 2
//
//        binding.violetCardView.startAnimation(animation)

//        val animator = ValueAnimator.ofFloat(0f, 100f)
//        animator.duration = 1000
//        animator.start()
//
//        animator.addUpdateListener { animation ->
//            val animatedValue = animation.animatedValue as Float
//            binding.violetCardView.translationX = animatedValue
//        }


        val floatViews = arrayListOf(
            binding.violetCardView,
            binding.lilacCardView,
            binding.lavenderCardView,
            binding.miniLavenderCardView
        )

        for (views in floatViews) {
            views.bringToFront()
        }

        val animation1 = AnimationUtils.loadAnimation(context, R.anim.onboarding_slide_right)

        for (i in 0..2) {
            floatViews[i].startAnimation(animation1)
        }

        floatViews[3].startAnimation(AnimationUtils.loadAnimation(context, R.anim.onboarding_slide_left))

        return binding.root
    }
}