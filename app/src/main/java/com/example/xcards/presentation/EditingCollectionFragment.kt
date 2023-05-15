package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xcards.R
import com.example.xcards.data.CardData
import com.example.xcards.databinding.FragmentEditingCollectionBinding

class EditingCollectionFragment(private val cardData: CardData) : Fragment() {
    private lateinit var binding: FragmentEditingCollectionBinding

//    companion object {
//        fun newInstance() = EditingCollectionFragment(cardData)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditingCollectionBinding.inflate(layoutInflater)

        binding.cardName.text = cardData.nameModule
        binding.cardsCount.text = cardData.cardsCount.toString()
        binding.mainCard.setCardBackgroundColor(resources.getColor(cardData.color))

        binding.backArrow.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        binding.deleteButton.setOnClickListener {

        }

        binding.editButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .add(R.id.mainFragmentContainer, CreatingCardFragment())
                .commit()
        }

        binding.mainCard.setOnClickListener {

        }

        return binding.root
    }
}