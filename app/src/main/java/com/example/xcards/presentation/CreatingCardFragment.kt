package com.example.xcards.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.xcards.databinding.FragmentCreatingCardBinding

class CreatingCardFragment : Fragment() {

    private lateinit var binding: FragmentCreatingCardBinding
    private lateinit var newModuleCardsData: HashMap<String, String>

    companion object {
        fun newInstance() = CreatingCardFragment()
    }

    private lateinit var viewModel: CreatingCardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatingCardBinding.inflate(layoutInflater)

        binding.addCardButton.setOnClickListener {

        }

        return binding.root
    }

    private fun uploadData() {
        binding!!.saveCardView.setOnClickListener {
//            newModuleCardsData = hashMapOf<String, String>(
//                "name" to "John doe",
//                "city" to "Nairobi"
//            )

//            FirebaseUtils().fireStoreDatabase.collection("users")
//                .document(userEmail)
//                .collection("cards")
//                .add(newModuleCards)
//                .addOnSuccessListener {
//                    Log.d(TAG, "Added document with ID ${it.id}")
//                }
//                .addOnFailureListener {
//                    Log.w(TAG, "Error with adding document")
//                }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreatingCardViewModel::class.java)
        // TODO: Use the ViewModel
    }
}