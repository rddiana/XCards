package com.example.xcards.presentation.UI.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xcards.R
import com.example.xcards.databinding.FragmentDisplayingCardsPreviewBinding
import com.example.xcards.domain.useCases.FirebaseDatabaseUtils

class DisplayingCardsPreviewFragment(
    val nameModule: String
) : Fragment() {
    private lateinit var binding: FragmentDisplayingCardsPreviewBinding
    private lateinit var database: FirebaseDatabaseUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisplayingCardsPreviewBinding.inflate(layoutInflater)
        database = FirebaseDatabaseUtils(requireContext().applicationContext)

        database.getCardsCollection(nameModule) { list ->
            binding.continueButton.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainFragmentContainer, DisplayingCardsFragment(list))
                    .commit()
            }
        }

        binding.toEditingCollection.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        return binding.root
    }
}