package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xcards.R
import com.example.xcards.databinding.FragmentDisplayingCardsPreviewBinding

class DisplayingCardsPreviewFragment : Fragment() {
    private lateinit var binding: FragmentDisplayingCardsPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayingCardsPreviewBinding.inflate(layoutInflater)

        binding.continueButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.mainFragmentContainer, DisplayingCardsFragment())
                .commit()
        }

        return binding.root
    }
}