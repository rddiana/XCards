package com.example.xcards.presentation

import android.app.TimePickerDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.xcards.R
import com.example.xcards.databinding.FragmentRemindersBinding
import java.util.*


class RemindersFragment : Fragment() {
    private lateinit var binding: FragmentRemindersBinding

    companion object {
        fun newInstance() = RemindersFragment()
    }

    private lateinit var viewModel: RemindersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRemindersBinding.inflate(layoutInflater)

        val currentNightMode = requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        binding.switchForReminders.setOnCheckedChangeListener { _, isChecked ->
            var newColorForText = resources.getColor(R.color.dark_gray)
            var newColorForButtons = resources.getColor(R.color.gray)
            var isEnable = false

            if (isChecked) {
                when (currentNightMode) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        newColorForText = resources.getColor(R.color.black)
                    }
                    Configuration.UI_MODE_NIGHT_YES -> {
                        newColorForText = resources.getColor(R.color.white)
                    }
                }

                newColorForButtons = resources.getColor(R.color.lilac)
                isEnable = true
            }

            binding.buttonViewHours.isEnabled = isEnable
            binding.buttonViewMinutes.isEnabled = isEnable

            binding.buttonViewHours.setBackgroundColor(newColorForButtons)
            binding.buttonViewMinutes.setBackgroundColor(newColorForButtons)

            binding.MondayCardView.setBackgroundColor(newColorForButtons)

            binding.viewLine1.setBackgroundColor(newColorForText)
            binding.settingTime.setTextColor(newColorForText)
            binding.settingTimeAdvise.setTextColor(newColorForText)
            binding.viewLine2.setBackgroundColor(newColorForText)
            binding.settingDaysOfWeek.setTextColor(newColorForText)
            binding.settingDaysOfWeekAdvise.setTextColor(newColorForText)

        }

        binding.buttonViewHours.setOnClickListener {
            showTimePicker()
        }

        binding.buttonViewMinutes.setOnClickListener {
            showTimePicker()
        }

        return binding.root
    }

    private fun showTimePicker() {
        val calender = Calendar.getInstance()
        val hours = calender.get(Calendar.HOUR_OF_DAY)
        val minutes = calender.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            R.style.timePickerStyle,
            { _, hourOfDay, minute ->
                binding.buttonViewHours.text = hourOfDay.toString()
                binding.buttonViewMinutes.text = minute.toString()
            },
            hours,
            minutes,
            false
        )

        timePickerDialog.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RemindersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}