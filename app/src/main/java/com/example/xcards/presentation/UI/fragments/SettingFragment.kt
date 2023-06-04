package com.example.xcards.presentation.UI.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.xcards.R
import com.example.xcards.databinding.FragmentSettingBinding
import com.example.xcards.data.sharedPref.SharedPreference
import java.util.*


class SettingFragment : Fragment() {
    private lateinit var binding: FragmentSettingBinding
    private lateinit var sharedPreference: SharedPreference

    private lateinit var currentLanguage: String
    private lateinit var languages: Array<String>

    init {
    }
    companion object {
        fun newInstance() = SettingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)

        sharedPreference = SharedPreference(requireContext().applicationContext)

        setCheckedRadioButton()

        binding.toPreviousFragment.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        binding.toRemindersFragment.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, RemindersFragment())
                .commit()
        }

        binding.toHardRepetionsFragment.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, HardRepetitionsFragment())
                .commit()
        }

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

        currentLanguage = Locale.getDefault().displayLanguage.toString()

        val en = resources.getString(R.string.english)
        val ru = resources.getString(R.string.russian)
        languages = arrayOf(en, ru)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_right_aligned, languages)
        binding.spinner.adapter = arrayAdapter

        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> setLocale("en")
                    1 -> setLocale("ru")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

//        ArrayAdapter.createFromResource(
//            requireContext(),
//            R.array.languages,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            binding.spinner.adapter = adapter
//        }
//
//        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                Log.d("spinner", )
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//        }

        binding.goalTextView.text = sharedPreference.getValueString("goal")?:"0"
        binding.changeGoalSlider.value = sharedPreference.getValueString("goal")?.toFloat() ?: 0f

        binding.changeGoalSlider.addOnChangeListener { _, value, _ ->
            binding.goalTextView.text = value.toInt().toString()
            sharedPreference.updateStringValue("goal", binding.goalTextView.text.toString())
        }

        return binding.root
    }


    private fun setLocale(localeName: String) {
        if (localeName != currentLanguage) {
            val locale = Locale(localeName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)

//            parentFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, SettingFragment()).commit()

//            startActivity(Intent(context, MainActivity::class.java).putExtra(currentLanguage, localeName))
        }
    }

    //Закрашивает нужные кружочки в RadioButton, так как они не сохраняются после переходов
    private fun setCheckedRadioButton() {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> binding.toDarkTheme.isChecked = true
            Configuration.UI_MODE_NIGHT_NO -> binding.toLightTheme.isChecked = true
        }
    }
}