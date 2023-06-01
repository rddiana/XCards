package com.example.xcards.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import swipeable.com.layoutmanager.OnItemSwiped
import swipeable.com.layoutmanager.SwipeableLayoutManager
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper

class DisplayingCardsFragment(
    private val listCardDataContent: List<CardContentData>
) : Fragment() {
    private lateinit var binding: FragmentDisplayingCardsBinding
    private lateinit var database: FirebaseDatabaseUtils

    private lateinit var adapter: DisplayingCardsAdapter
    private lateinit var swipeLayoutManager: SwipeableLayoutManager
    private lateinit var swipeTouchHelper: SwipeableTouchHelperCallback
    private lateinit var itemTouchHelper: ItemTouchHelper

    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisplayingCardsBinding.inflate(layoutInflater)
        database = FirebaseDatabaseUtils(requireContext().applicationContext)

        adapter = DisplayingCardsAdapter(
            requireContext(),
            ArrayList(listCardDataContent),
            resources
        )

        swipeTouchHelper = SwipeableTouchHelperCallback(object : OnItemSwiped {
            override fun onItemSwiped() {
                adapter.removeSwipedItem()
            }

            override fun onItemSwipedLeft() {
                Log.e("SWIPE", "LEFT")
            }

            override fun onItemSwipedRight() {
                Log.e("SWIPE", "RIGHT")
            }

            override fun onItemSwipedUp() {

            }

            override fun onItemSwipedDown() {

            }
        })

        itemTouchHelper = ItemTouchHelper(swipeTouchHelper)
        itemTouchHelper.attachToRecyclerView(binding.displayingCardRecyclerView)

        swipeLayoutManager = SwipeableLayoutManager()
        swipeLayoutManager.angle = 10
        swipeLayoutManager.animationDuratuion = 1000
        swipeLayoutManager.maxShowCount = 3
        swipeLayoutManager.scaleGap = 0.5f
        swipeLayoutManager.transYGap = 0

        binding.displayingCardRecyclerView.layoutManager = swipeLayoutManager

        binding.displayingCardRecyclerView.adapter = adapter

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