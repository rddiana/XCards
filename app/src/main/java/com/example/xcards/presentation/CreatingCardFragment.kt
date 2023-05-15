package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.xcards.R
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

        val firstFragment = CardForCreatingCardFragment()
        val fragmentManager: FragmentManager? = fragmentManager
        fragmentManager?.beginTransaction()
            ?.add(R.id.containerForEditText, firstFragment)
            ?.commit()

        binding.toPreviousFragment2.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        binding.addCardButton.setOnClickListener {
            val myFragment = CardForCreatingCardFragment()

            fragmentManager?.beginTransaction()
                ?.add(R.id.containerForEditText, CardForCreatingCardFragment())
                ?.commit()
        }

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

        return binding.root
    }

    private fun removeItem(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.remove(fragment)?.commit()
    }

    private fun changeViewBgColor(color: Int) {
        binding.saveCardView.setCardBackgroundColor(resources.getColor(color))
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