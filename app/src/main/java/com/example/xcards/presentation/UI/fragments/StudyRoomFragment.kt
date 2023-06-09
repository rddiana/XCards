package com.example.xcards.presentation.UI.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.xcards.R
import com.example.xcards.data.dataClasses.CardData
import com.example.xcards.databinding.FragmentStudyRoomBinding
import com.example.xcards.presentation.adapters.AdapterForRecyclerView
import com.example.xcards.domain.useCases.FirebaseDatabaseUtils


class StudyRoomFragment() : Fragment() {
    private lateinit var binding: FragmentStudyRoomBinding

    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager

    private lateinit var database: FirebaseDatabaseUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudyRoomBinding.inflate(layoutInflater)

        database = FirebaseDatabaseUtils(requireContext().applicationContext)

        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = staggeredGridLayoutManager

        binding.recyclerView.adapter = AdapterForRecyclerView(
            context,
            ArrayList(),
            { card ->
                onCardPressed(card)
            },
            { onPlusBtPressed() },
            { onStartPressed() }
        )

        database.getAllCardsInfo {
            binding.recyclerView.adapter = AdapterForRecyclerView(
                context,
                ArrayList(it),
                { card ->
                    onCardPressed(card)
                },
                { onPlusBtPressed() },
                { onStartPressed() }
            )
        }

        view?.findViewById<CardView>(R.id.cardViewWithBtNewCards)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFragmentContainer, CreatingCardFragment(CardData("", 0, "")))
                .commit()
        }

        return binding.root
    }

    private fun onCardPressed(cardData: CardData) {
        parentFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer, EditingCollectionFragment(cardData))
            .commit()
    }

    private fun onPlusBtPressed() {
        parentFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer, CreatingCardFragment(CardData("", 0, "")))
            .commit()
    }

    private fun onStartPressed() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}