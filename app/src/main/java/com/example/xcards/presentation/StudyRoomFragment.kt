package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.xcards.R
import com.example.xcards.data.CardData
import com.example.xcards.databinding.FragmentStudyRoomBinding
import com.example.xcards.domain.adapters.AdapterForRecyclerView
import com.example.xcards.domain.useCase.FirebaseDatabaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.collections.ArrayList


class StudyRoomFragment() : Fragment() {
    private lateinit var binding: FragmentStudyRoomBinding
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var adapterForRecyclerView: AdapterForRecyclerView

    private lateinit var database: FirebaseDatabaseUtils

    companion object {
        fun newInstance() = StudyRoomFragment()
    }

    private lateinit var viewModel: StudyRoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyRoomBinding.inflate(layoutInflater)

        database = FirebaseDatabaseUtils(requireContext().applicationContext)

        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = staggeredGridLayoutManager

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
                .replace(R.id.mainFragmentContainer, CreatingCardFragment(null))
                .commit()
        }

        return binding.root
    }

    fun onSuccess(cards: ArrayList<CardData>) {
        adapterForRecyclerView =
            AdapterForRecyclerView(
                context,
                cards,
                { onCardPressed(it) },
                { onPlusBtPressed() },
                { onStartPressed() }
            )
    }

    private fun onCardPressed(cardData: CardData) {
        parentFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer, EditingCollectionFragment(cardData))
            .commit()
    }

    private fun onPlusBtPressed() {
        parentFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer, CreatingCardFragment(null))
            .commit()
    }

    private fun onStartPressed() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StudyRoomViewModel::class.java)
        // TODO: Use the ViewModel
    }

}