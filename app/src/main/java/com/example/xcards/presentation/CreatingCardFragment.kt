package com.example.xcards.presentation
//!!

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.xcards.R
import com.example.xcards.data.CardContentData
import com.example.xcards.databinding.FragmentCreatingCardBinding
import com.example.xcards.domain.adapters.AdapterForNewCards
import com.example.xcards.domain.useCase.SharedPreference
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class CreatingCardFragment(val nameCollection: String?) : Fragment() {

    private lateinit var binding: FragmentCreatingCardBinding
    private var displayingData: ArrayList<CardContentData> = ArrayList()

    private lateinit var database: DatabaseReference
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

        database = Firebase.database.reference
        sharedPreference = SharedPreference(requireContext().applicationContext)
        id = sharedPreference.getValueString("uid").toString()


//        nameCollection?.let {
//            database.child(id).child(it).get().addOnCompleteListener { snapshot ->
//                val displayingData = snapshot.result.value as? ArrayList<NewCardData>
//            } }

        if (!nameCollection.isNullOrEmpty()) {
            binding.newNameCollectionText.setText(nameCollection)
        }

        nameCollection?.let {
            database.child(id).child("cardCollection").child(it).child("cards")
                .get().addOnCompleteListener { snapshot ->
                    displayingData = (snapshot.result.value as? ArrayList<CardContentData>)!!
                }
        }

        adapterForNewCards = AdapterForNewCards(
            context,
            displayingData
        )

        gridLayoutManager = GridLayoutManager(context, 1)
        binding.containerForCreatingCardsFragments.layoutManager = gridLayoutManager
        binding.containerForCreatingCardsFragments.adapter = adapterForNewCards

        binding.toPreviousFragment2.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
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
        val confirmedNameCollection = nameCollection.toString()

        database
            .child(id)
            .child("cardCollection")
            .child(getCollectionName())
            .child("cards")
            .setValue(displayingData)

        database
            .child(id)
            .child("cards")
            .child(getCollectionName())
            .child("info")
            .child("color")
            .setValue(binding.saveCardView.cardBackgroundColor.defaultColor)

        database
            .child(id)
            .child("cards")
            .child(getCollectionName())
            .child("info")
            .child("cardsCount")
            .setValue(displayingData.size)

        val cardsRef = database.child(id).child("cardCollections")

        if (newNameCollection.isNullOrEmpty()) {
            Toast.makeText(context, R.string.error_collection_name_is_empty, Toast.LENGTH_SHORT)
                .show()
        } else if (!confirmedNameCollection.isNullOrEmpty() && confirmedNameCollection == newNameCollection
        ) {
//            Сохранение изменений, если имя коллекции существовало и не изменилось.

            cardsRef.child(confirmedNameCollection).child("cards").setValue(displayingData)
                .addOnSuccessListener {
                    Toast.makeText(context, R.string.updates_saved_successfully, Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, R.string.save_updates_failed, Toast.LENGTH_SHORT).show()
                }

//                closeThisFragment()
        } else if (confirmedNameCollection.isNullOrEmpty()) {
            /*
            Создание коллекции в базе, если:
            - Коллекции до этого не существовало, а новое введенное имя из EditText не пустое и не null.
             */

            cardsRef.child(newNameCollection).child("cards").setValue(displayingData)
                .addOnSuccessListener {
                    Toast.makeText(context, R.string.on_successful_creation_collection, Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, R.string.on_collection_creation_failed, Toast.LENGTH_SHORT).show()
                }

//                closeThisFragment()
        } else if (!newNameCollection.equals(confirmedNameCollection) && newNameCollection.isNullOrEmpty()) {
            /*
            Удаление старой ветви и создание новой с измененным названием, если:
            - имя изменено, причем новое введенное имя из EditText не пустое и не null.
             */

            cardsRef.child(confirmedNameCollection).removeValue()
            cardsRef.child(newNameCollection).child("cards").setValue(displayingData)
                .addOnSuccessListener {
                    Toast.makeText(context, R.string.on_successful_creation_collection, Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, R.string.on_collection_creation_failed, Toast.LENGTH_SHORT).show()
                }

//                closeThisFragment()
        } else {
            Toast.makeText(context, "Нерассмотренный случай", Toast.LENGTH_SHORT).show()
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