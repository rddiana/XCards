package com.example.xcards.presentation
//!!

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xcards.R
import com.example.xcards.data.CardContentData
import com.example.xcards.data.CardData
import com.example.xcards.databinding.FragmentCreatingCardBinding
import com.example.xcards.domain.adapters.AdapterForNewCards
import com.example.xcards.domain.useCase.FirebaseDatabaseUtils
import com.example.xcards.domain.useCase.SharedPreference


class CreatingCardFragment(val cardData: CardData) : Fragment() {

    private lateinit var binding: FragmentCreatingCardBinding
    private var displayingData: ArrayList<CardContentData> = ArrayList()

    private lateinit var database: FirebaseDatabaseUtils
    private lateinit var sharedPreference: SharedPreference

    private lateinit var adapterForNewCards: AdapterForNewCards

//    companion object {
//        fun newInstance() = CreatingCardFragment()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatingCardBinding.inflate(layoutInflater)

        database = FirebaseDatabaseUtils(requireContext().applicationContext)
        sharedPreference = SharedPreference(requireContext().applicationContext)

        binding.containerForCreatingCardsFragments.layoutManager =
            LinearLayoutManager(requireContext())

        binding.addCardButton.setOnClickListener {
            displayingData.add(CardContentData("", ""))
            adapterForNewCards = AdapterForNewCards(requireContext(), displayingData)
            binding.containerForCreatingCardsFragments.adapter = adapterForNewCards
        }

        if (cardData.nameModule.isNotEmpty()) {
            binding.newNameCollectionText.setText(cardData.nameModule)

            database.getCardsCollection(cardData.nameModule) {
                displayingData = ArrayList(it)

                adapterForNewCards = AdapterForNewCards(
                    context,
                    displayingData
                )

                binding.containerForCreatingCardsFragments.adapter = adapterForNewCards
            }

            binding.saveCardView.setCardBackgroundColor(cardData.color.toLong(radix = 16).toInt())
        } else {
            adapterForNewCards = AdapterForNewCards(
                context,
                displayingData
            )
            binding.containerForCreatingCardsFragments.adapter = adapterForNewCards
        }

        binding.toPreviousFragment2.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFragmentContainer, StudyRoomFragment()).commit()
        }

        binding.saveCardView.setOnClickListener {
            uploadData()
        }

        setOnClickListenersForChangingColor()

        return binding.root
    }

    private fun uploadData() {
        val newNameCollection = getCollectionName()

        if (cardData.nameModule.isEmpty()) {
            if (newNameCollection.isEmpty() || newNameCollection.isBlank()) {
                Toast.makeText(context, R.string.error_collection_name_is_empty, Toast.LENGTH_SHORT)
                    .show()
            } else {
                database.saveNewCollectionInfo(
                    CardData(
                        newNameCollection,
                        binding.containerForCreatingCardsFragments.adapter!!.itemCount,
                        Integer.toHexString(binding.saveCardView.cardBackgroundColor.defaultColor)
                    )
                )

                database.saveNewCardsData(
                    requireContext(),
                    newNameCollection,
                    adapterForNewCards.cardContentArray
                )
            }
        } else {
            if (!cardData.nameModule.equals(newNameCollection)) {
                database.updateCollectionName(cardData.nameModule, newNameCollection)
            }

            database.updateCollectionInfo(
                CardData(
                    newNameCollection,
                    binding.containerForCreatingCardsFragments.adapter!!.itemCount,
                    Integer.toHexString(binding.saveCardView.cardBackgroundColor.defaultColor)
                )
            )

            database.updateCardsData(
                requireContext(),
                newNameCollection,
                adapterForNewCards.cardContentArray
            )
        }

        /*
        Также нужна проверка на:
        - Коллекций с одинаковыми именами не должно быть в базе
         */
    }

    private fun closeThisFragment() {
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

    private fun getCollectionName(): String {
        return binding.newNameCollectionText.text.toString()
    }

    private fun changeViewBgColor(color: Int) {
        binding.saveCardView.setCardBackgroundColor(resources.getColor(color))
    }

    private fun setOnClickListenersForChangingColor() {
        binding.redButton.setOnClickListener {
            changeViewBgColor(R.color.light_red)
        }

        binding.orangeButton.setOnClickListener {
            changeViewBgColor(R.color.orange)
        }

        binding.yellowButton.setOnClickListener {
            changeViewBgColor(R.color.yellow)
        }

        binding.lightGreenButton.setOnClickListener {
            changeViewBgColor(R.color.light_green)
        }

        binding.darkGreenButton.setOnClickListener {
            changeViewBgColor(R.color.green)
        }

        binding.lightBlueButton.setOnClickListener {
            changeViewBgColor(R.color.light_blue)
        }

        binding.darkBlueButton.setOnClickListener {
            changeViewBgColor(R.color.dark_blue)
        }

        binding.purpleButton.setOnClickListener {
            changeViewBgColor(R.color.purple)
        }

        binding.braunButton.setOnClickListener {
            changeViewBgColor(R.color.braun)
        }

        binding.whiteButton.setOnClickListener {
            changeViewBgColor(R.color.light_gray)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}