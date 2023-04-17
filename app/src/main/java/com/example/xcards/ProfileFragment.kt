package com.example.xcards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.xcards.data.implementations.SharedPreferencesRepositoryImpl
import com.example.xcards.domain.repositories.SharedPreferencesRepository

class ProfileFragment : Fragment() {

    private lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)

        sharedPreferencesRepository = SharedPreferencesRepositoryImpl(this)


    }

}