package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xcards.R
import com.example.xcards.data.CardContentData
import com.example.xcards.databinding.FragmentModuleIsFinishedBinding
import com.example.xcards.domain.adapters.AdapterForSummingUpTheResults

class ModuleIsFinishedFragment(
    private val cardContentList: List<CardContentData>,
    private val correctAnswerList: List<Boolean>
) : Fragment() {
    private lateinit var binding: FragmentModuleIsFinishedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModuleIsFinishedBinding.inflate(layoutInflater)

        binding.backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer, StudyRoomFragment()
            ).commit()
        }

        var countCorrectAnswer = 0
        correctAnswerList.forEach {
            if (it) { countCorrectAnswer++ }
        }

        binding.correctAnswers.text = countCorrectAnswer.toString()
        binding.wholeNumberOfQuestion.text = cardContentList.size.toString()

        if (countCorrectAnswer == cardContentList.size) {
            binding.repeatForgottenCardView.visibility = View.INVISIBLE

            val params = binding.recyclerViewForResults.layoutParams as ConstraintLayout.LayoutParams
            params.topToBottom = binding.startOverAgainCardView.id
            binding.recyclerViewForResults.requestLayout()
        } else {
            binding.repeatForgottenCardView.setOnClickListener {
                if (countCorrectAnswer > 0) {
                    val incorrectAnswerList = arrayListOf<CardContentData>()

                    correctAnswerList.forEachIndexed { index, value ->
                        if (! value) {
                            incorrectAnswerList.add(cardContentList[index])
                        }
                    }

                    parentFragmentManager.beginTransaction().replace(
                        R.id.mainFragmentContainer, DisplayingCardsFragment(incorrectAnswerList)
                    ).commit()
                }
            }
        }

        binding.recyclerViewForResults.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.recyclerViewForResults.adapter =
            AdapterForSummingUpTheResults(requireContext(), cardContentList, correctAnswerList)

        binding.startOverAgainCardView.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer, DisplayingCardsFragment(cardContentList)
            ).commit()
        }

        return binding.root
    }
}