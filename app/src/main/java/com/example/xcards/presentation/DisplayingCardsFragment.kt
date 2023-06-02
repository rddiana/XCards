package com.example.xcards.presentation

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.example.xcards.R
import com.example.xcards.data.CardContentData
import com.example.xcards.databinding.FragmentDisplayingCardsBinding
import com.example.xcards.domain.useCase.FirebaseDatabaseUtils


class DisplayingCardsFragment(
    private val cardContentList: List<CardContentData>
) : Fragment() {
    private lateinit var binding: FragmentDisplayingCardsBinding
    private lateinit var database: FirebaseDatabaseUtils

    private var position = 0
    private var isCardFlipped = false
    private var isAnswersCorrectList = arrayListOf<Boolean>()
    private var viewHeight: Int = 0
    private var viewWidth: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayingCardsBinding.inflate(layoutInflater)
        database = FirebaseDatabaseUtils(requireContext().applicationContext)

        binding.viewQuestion.findViewById<TextView>(R.id.cardQuestionText).text = cardContentList[position].question
        binding.viewAnswer.findViewById<TextView>(R.id.cardAnswerText).text = cardContentList[position].answer

        binding.viewAnswer.setOnClickListener {
            flipCard(requireContext(), binding.viewQuestion, it)
            isCardFlipped = false
        }

        binding.viewQuestion.setOnClickListener {
            flipCard(requireContext(), binding.viewAnswer, it)
            isCardFlipped = true
        }

        viewHeight = binding.progressLinearLayout.height
        viewWidth = binding.progressLinearLayout.width / cardContentList.size

        binding.dontRememberCardView.setOnClickListener {
            isAnswersCorrectList.add(position, false)

            val view = View(requireContext())
            when (position) {
                0 -> {
                    view.background = ContextCompat.getDrawable(requireContext(), R.drawable.progress_rect_pink_start)
                }
                cardContentList.size - 1 -> {
                    view.background = ContextCompat.getDrawable(requireContext(), R.drawable.progress_rect_pink_end)
                }
                else -> {
                    view.background = ContextCompat.getDrawable(requireContext(), R.drawable.progress_rect_pink_center)
                }
            }

            val params = ViewGroup.LayoutParams(
                viewWidth,
                viewHeight
            )

            view.layoutParams = params

//            view.layoutParams.height = viewHeight
//            view.layoutParams.width = viewWidth

            binding.progressLinearLayout.addView(view)
            showNextCard()
        }

        binding.rememberCardView.setOnClickListener {
            isAnswersCorrectList.add(position, true)

            val view = View(requireContext())
            when (position) {
                0 -> {
                    view.background = ContextCompat.getDrawable(requireContext(), R.drawable.progress_rect_green_start)
                }
                cardContentList.size - 1 -> {
                    view.background = ContextCompat.getDrawable(requireContext(), R.drawable.progress_rect_green_end)
                }
                else -> {
                    view.background = ContextCompat.getDrawable(requireContext(), R.drawable.progress_rect_green_center)
                }
            }

            val params = ViewGroup.LayoutParams(
                viewWidth,
                viewHeight
            )

            view.layoutParams = params

//            view.layoutParams.height = viewHeight
//            view.layoutParams.width = viewWidth

            binding.progressLinearLayout.addView(view)
            showNextCard()
        }

        binding.backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer, StudyRoomFragment()
            ).commit()
        }

        return binding.root
    }

    private fun showNextCard() {
        position++
        if (position < cardContentList.size) {
            if (isCardFlipped) {
                flipCard(requireContext(), binding.viewQuestion, binding.viewAnswer)
            }

            binding.viewQuestion.findViewById<TextView>(R.id.cardQuestionText).text =
                cardContentList[position].question
            binding.viewAnswer.findViewById<TextView>(R.id.cardAnswerText).text =
                cardContentList[position].answer
        } else {
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer, ModuleIsFinishedFragment(cardContentList, isAnswersCorrectList.toList())
            ).commit()
        }
    }

    private fun flipCard(context: Context, visibleView: View, inVisibleView: View) {
        try {
            visibleView.visibility = View.VISIBLE

            val scale = context.resources.displayMetrics.density
            val cameraDist = 8000 * scale
            visibleView.cameraDistance = cameraDist
            inVisibleView.cameraDistance = cameraDist

            val flipOutAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_out
                ) as AnimatorSet

            flipOutAnimatorSet.setTarget(inVisibleView)

            val flipInAnimationSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_in
                ) as AnimatorSet

            flipInAnimationSet.setTarget(visibleView)
            flipOutAnimatorSet.start()
            flipInAnimationSet.start()
            flipInAnimationSet.doOnEnd {
                inVisibleView.isGone = true
            }
        } catch (e: Exception) {
            Log.d("animator exception", e.message.toString())
        }
    }

    private fun onRightToLeftSwipe(view: View) {
        Toast.makeText(
            view.context, "right to left",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onLeftToRightSwipe(view: View) {
        Toast.makeText(
            view.context, "left to right",
            Toast.LENGTH_SHORT
        ).show()
    }
}