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
import com.example.xcards.domain.useCase.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


class StudyRoomFragment : Fragment() {
    private lateinit var binding: FragmentStudyRoomBinding
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var adapterForRecyclerView: AdapterForRecyclerView

    private lateinit var database: FirebaseDatabase
    private lateinit var currentUser: FirebaseUser

    companion object {
        fun newInstance() = StudyRoomFragment()
    }

    private lateinit var viewModel: StudyRoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyRoomBinding.inflate(layoutInflater)

        val cards: ArrayList<CardData> = List(3) {
            CardData("Test", 3, Integer.toHexString(R.color.sky_blue))
        } as ArrayList<CardData>

        database = FirebaseDatabase(requireContext().applicationContext)
        currentUser = FirebaseAuth.getInstance().currentUser!!

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
                .add(R.id.mainFragmentContainer, CreatingCardFragment(null))
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