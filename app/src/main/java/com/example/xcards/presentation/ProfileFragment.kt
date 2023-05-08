package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.xcards.R
import com.example.xcards.data.Constants
import com.example.xcards.data.User
import com.example.xcards.data.implementations.SharedPreferencesRepositoryImpl
import com.example.xcards.databinding.FragmentProfileBinding
import com.example.xcards.domain.repositories.SharedPreferencesRepository
import com.example.xcards.domain.useCase.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private lateinit var sharedPreferencesRepository: SharedPreferencesRepository
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        sharedPreferencesRepository = SharedPreferencesRepositoryImpl(requireContext())
//        binding.textViewUserName.text = sharedPreferencesRepository.loadString(Constants.NAME_KEY_PREF)
//        binding.textViewEmail.text = sharedPreferencesRepository.loadString(Constants.EMAIL_KEY_PREF)

        val userData = FirebaseUtils()

        binding.toSettingFragment.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.fragmentContainer, SettingFragment())
                .commit()
        }

        return binding.root
    }
}