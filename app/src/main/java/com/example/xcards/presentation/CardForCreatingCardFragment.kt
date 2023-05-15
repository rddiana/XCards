package com.example.xcards.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.xcards.R
import com.example.xcards.databinding.FragmentCardForCreatingCardBinding
import com.google.firebase.database.core.Context

class CardForCreatingCardFragment() : Fragment() {
    private lateinit var binding: FragmentCardForCreatingCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardForCreatingCardBinding.inflate(layoutInflater)

        binding.removeCard.setOnClickListener {

        }

        return binding.root
    }

}