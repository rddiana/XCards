package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xcards.R
import com.example.xcards.databinding.FragmentProfileBinding
import com.example.xcards.domain.useCase.SharedPreference
import com.example.xcards.presentation.activities.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private lateinit var sharedPreference: SharedPreference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        database = Firebase.database.reference
        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreference = SharedPreference(requireContext().applicationContext)

        binding.textViewUserName.text = sharedPreference.getValueString("userName")
        binding.textViewEmail.text = sharedPreference.getValueString("email")

        binding.toSettingFragment.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, SettingFragment())
                .commit()
        }

        return binding.root
    }
}