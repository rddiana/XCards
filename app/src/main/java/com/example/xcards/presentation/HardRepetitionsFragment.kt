package com.example.xcards.presentation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.xcards.R

class HardRepetitionsFragment : Fragment() {

    companion object {
        fun newInstance() = HardRepetitionsFragment()
    }

    private lateinit var viewModel: HardRepetitionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hard_repetitions, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HardRepetitionsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}