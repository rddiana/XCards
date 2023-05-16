package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.xcards.R
import com.example.xcards.data.CardData
import com.example.xcards.databinding.FragmentStudyRoomBinding
import com.example.xcards.domain.adapters.AdapterForRecyclerView
import java.util.*


class StudyRoomFragment : Fragment() {
    private lateinit var binding: FragmentStudyRoomBinding
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var adapterForRecyclerView: AdapterForRecyclerView

    companion object {
        fun newInstance() = StudyRoomFragment()
    }

    private lateinit var viewModel: StudyRoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyRoomBinding.inflate(layoutInflater)

        val buttonsAsCard = inflater.inflate(R.layout.buttons, null) as CardView
        val newCard = inflater.inflate(R.layout.card, null) as CardView

        val cards: ArrayList<CardData> = List(3) {
            CardData("Test", 3, R.color.sky_blue)
        } as ArrayList<CardData>

        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = staggeredGridLayoutManager
        adapterForRecyclerView =
            AdapterForRecyclerView(
                context,
                cards,
                { onCardPressed(it) },
                { onPlusBtPressed() },
                { onStartPressed() }
            )

        view?.findViewById<CardView>(R.id.cardViewWithBtNewCards)?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.mainFragmentContainer, CreatingCardFragment())
                .commit()
        }

        binding.recyclerView.adapter = adapterForRecyclerView

        return binding.root
    }

    private fun onCardPressed(cardData: CardData) {
        parentFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer, EditingCollectionFragment(cardData))
            .commit()
    }

    private fun onPlusBtPressed() {
        parentFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer, CreatingCardFragment())
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