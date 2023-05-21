package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.xcards.R
import com.example.xcards.data.NewCardData
import com.example.xcards.databinding.FragmentCreatingCardBinding
import com.example.xcards.domain.adapters.AdapterForNewCards
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class CreatingCardFragment(val nameCollection: String?) : Fragment() {

    private lateinit var binding: FragmentCreatingCardBinding
    private var newCardArray: ArrayList<NewCardData>  = ArrayList()

    private lateinit var database: DatabaseReference
    private val userEmail = FirebaseAuth.getInstance().currentUser?.email.toString()

    private lateinit var adapterForNewCards: AdapterForNewCards
//
//    companion object {
//        fun newInstance() = CreatingCardFragment()
//    }

    private lateinit var viewModel: CreatingCardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatingCardBinding.inflate(layoutInflater)

        database = Firebase.database.reference

        adapterForNewCards = AdapterForNewCards(
            context,
            newCardArray)

        nameCollection?.let {
            database.child(userEmail).child(it).get().addOnCompleteListener { snapshot ->
                val displayingData = snapshot.result.value as? ArrayList<NewCardData>
            } }

        if (nameCollection != null) {
//            adapterForNewCards = AdapterForNewCards(
//                context,
//                database.child(userEmail).child(nameCollection).get() as ArrayList<NewCardData>)
        } else {

        }



        binding.toPreviousFragment2.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        binding.addCardButton.setOnClickListener {
            newCardArray.add(NewCardData("test", "test"))
            adapterForNewCards.notifyItemInserted(newCardArray.size - 1)
        }

        setOnClickListenersForChangingColor()

        return binding.root
    }

    private fun removeItem(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.remove(fragment)?.commit()
    }

    private fun changeViewBgColor(color: Int) {
        binding.saveCardView.setCardBackgroundColor(resources.getColor(color))
    }

    private fun uploadData() {
        binding.saveCardView.setOnClickListener {



            database.child(userEmail).child(getCollectionName()).setValue(newCardArray)
        }
    }

    private fun getCardsInfo() {

    }

    private fun getCollectionName(): String {
        return binding.newNameCollectionText.text.toString()
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