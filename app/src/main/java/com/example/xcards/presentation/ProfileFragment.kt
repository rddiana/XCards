package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xcards.R
import com.example.xcards.data.implementations.SharedPreferencesRepositoryImpl
import com.example.xcards.databinding.FragmentProfileBinding
import com.example.xcards.domain.repositories.SharedPreferencesRepository
import com.example.xcards.domain.useCase.FirebaseUtils

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
            parentFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, SettingFragment())
                .commit()
        }

        return binding.root
    }
}