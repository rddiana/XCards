package com.example.xcards.presentation
//!!

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.xcards.R
import com.example.xcards.data.CardContentData
import com.example.xcards.databinding.FragmentCreatingCardBinding
import com.example.xcards.domain.adapters.AdapterForNewCards
import com.example.xcards.domain.useCase.FirebaseDatabaseUtils
import com.example.xcards.domain.useCase.SharedPreference


class CreatingCardFragment(val nameCollection: String?) : Fragment() {

    private lateinit var binding: FragmentCreatingCardBinding
    private var displayingData: ArrayList<CardContentData> = ArrayList()

    private lateinit var database: FirebaseDatabaseUtils
    private lateinit var sharedPreference: SharedPreference

    private lateinit var id: String

    private lateinit var adapterForNewCards: AdapterForNewCards
    private lateinit var gridLayoutManager: GridLayoutManager

//    companion object {
//        fun newInstance() = CreatingCardFragment()
//    }

    private lateinit var viewModel: CreatingCardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatingCardBinding.inflate(layoutInflater)

        database = FirebaseDatabaseUtils(requireContext().applicationContext)
        sharedPreference = SharedPreference(requireContext().applicationContext)
        id = sharedPreference.getValueString("uid").toString()

        gridLayoutManager = GridLayoutManager(context, 1)
        binding.containerForCreatingCardsFragments.layoutManager = gridLayoutManager

        if (nameCollection != null) {
            binding.newNameCollectionText.setText(nameCollection)

            database.getCardsCollection (nameCollection) {
                binding.containerForCreatingCardsFragments.adapter = AdapterForNewCards(
                    context,
                    ArrayList(it)
                )
            }
        } else {
            binding.containerForCreatingCardsFragments.adapter = AdapterForNewCards(
                context,
                displayingData
            )
        }

        binding.toPreviousFragment2.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, StudyRoomFragment()).commit()
        }

        binding.addCardButton.setOnClickListener {
            displayingData.add(CardContentData("", ""))
            adapterForNewCards.notifyItemInserted(displayingData.size - 1)
        }

        binding.saveCardView.setOnClickListener {
            uploadData()
        }

        setOnClickListenersForChangingColor()

        return binding.root
    }

    private fun uploadData() {
        val newNameCollection = getCollectionName()

        if (nameCollection == null) {
            if (newNameCollection.isNullOrEmpty() || newNameCollection.isBlank()) {
                Toast.makeText(context, R.string.error_collection_name_is_empty, Toast.LENGTH_SHORT)
                    .show()
            } else {
                database.saveNewCollectionInfo(
                    newNameCollection,
                    (binding.saveCardView.cardBackgroundColor.defaultColor).toLong(),
                    adapterForNewCards.newCardsArray.size
                )

                database.saveNewCardsData(
                    requireContext(),
                    newNameCollection,
                    adapterForNewCards.newCardsArray
                )
            }
        } else {
            if (!nameCollection.toString().equals(newNameCollection)) {
                database.updateCollectionName(nameCollection, newNameCollection)
            }

            database.updateCollectionInfo(
                newNameCollection,
                "color",
                binding.saveCardView.cardBackgroundColor.defaultColor
            )

            database.updateCollectionInfo(
                newNameCollection,
                "cardsCount",
                adapterForNewCards.newCardsArray.size
            )

            database.updateCardsData(
                requireContext(),
                newNameCollection,
                adapterForNewCards.newCardsArray
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
        viewModel = ViewModelProvider(this).get(CreatingCardViewModel::class.java)
        // TODO: Use the ViewModel
    }
}