package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xcards.R
import com.example.xcards.databinding.FragmentCardForCreatingCardBinding
import com.google.android.material.card.MaterialCardView

class CardForCreatingCardFragment() : Fragment() {
    private lateinit var binding: FragmentCardForCreatingCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardForCreatingCardBinding.inflate(layoutInflater)

        binding.removeCard.setOnClickListener {

        }

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        view?.findViewById<MaterialCardView>(R.id.saveCardView)?.setOnClickListener {
//            val bundleForQuestion = Bundle()
//            bundleForQuestion.putString("question", binding.editTextForQuestion.text.toString())
//
//
//            val bundleForAnswer = Bundle()
//            bundleForAnswer.putString("answer", binding.editTextForAnswer.text.toString())
//        }
//    }

}