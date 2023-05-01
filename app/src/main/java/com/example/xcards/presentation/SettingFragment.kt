package com.example.xcards.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.xcards.R
import com.example.xcards.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel: SettingViewModel

    companion object {
        fun newInstance() = SettingFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)

        binding.toPreviousFragment.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentContainer, ProfileFragment())
                .commit()
        }

        binding.toRemindersFragment.setOnClickListener {
            parentFragmentManager.beginTransaction().add(R.id.fragmentContainer, RemindersFragment())
                .commit()
        }

//        val currentMode = resources.configuration.uiMode
//        if (currentMode == Configuration.UI_MODE_NIGHT_NO) {
//            binding.toLightTheme.isChecked = true
//        } else {
//            binding.toDarkTheme.isChecked = true
//        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.toLightTheme -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                R.id.toDarkTheme -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingViewModel::class.java]
        // TODO: Use the ViewModel
    }

}