package com.example.xcards.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xcards.R
import com.example.xcards.data.CardData
import com.example.xcards.databinding.FragmentHomeBinding
import com.example.xcards.domain.adapters.AdapterForMiniCards
import com.example.xcards.domain.adapters.AdapterForMiniCardsChangingColor
import com.example.xcards.domain.useCase.FirebaseDatabaseUtils

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var database: FirebaseDatabaseUtils
    private lateinit var displayingCards: List<CardData>

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        database = FirebaseDatabaseUtils(requireContext().applicationContext)

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val request = s.toString().trim()

                database.getAllCardsInfo {
                    val cardsNameList = listOf<String>()

                    it.forEach { cardData ->
                        cardsNameList.plus(cardData.nameModule)
                    }

                    val index = cardsNameList.indexOf(request)

                    if (index == -1) {
                        Toast.makeText(context, R.string.nothing_found, Toast.LENGTH_SHORT).show()
                    } else {
                        parentFragmentManager.beginTransaction().replace(
                            R.id.mainFragmentContainer,
                            EditingCollectionFragment(it[index])
                        ).commit()
                    }
                }
            }
        })

        binding.recyclerViewForAllCards.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        database.getAllCardsInfo {
            displayingCards = if (it.size >= 5) {
                it.slice(0..4)
            } else it

            binding.recyclerViewForAllCards.adapter = AdapterForMiniCards(
                context,
                ArrayList(displayingCards),
                R.layout.mini_cards_2
            )
        }

        binding.toStudyRoomCard.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.mainFragmentContainer, StudyRoomFragment())
                .commit()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}