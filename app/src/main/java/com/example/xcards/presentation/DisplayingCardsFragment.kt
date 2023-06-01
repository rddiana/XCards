package com.example.xcards.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.xcards.R
import com.example.xcards.data.CardContentData
import com.example.xcards.databinding.FragmentDisplayingCardsBinding
import com.example.xcards.domain.adapters.DisplayingCardsAdapter
import com.example.xcards.domain.useCase.FirebaseDatabaseUtils

class DisplayingCardsFragment(
    private val listCardDataContent: List<CardContentData>
) : Fragment() {
    private lateinit var binding: FragmentDisplayingCardsBinding
    private lateinit var database: FirebaseDatabaseUtils

    private lateinit var adapter: DisplayingCardsAdapter

    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisplayingCardsBinding.inflate(layoutInflater)
        database = FirebaseDatabaseUtils(requireContext().applicationContext)

        adapter = DisplayingCardsAdapter(
            requireContext(),
            ArrayList(listCardDataContent)
        )


        binding.backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer, StudyRoomFragment()
            ).commit()
        }

        return binding.root
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