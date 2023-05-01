package com.example.xcards.presentation

import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.xcards.R
import com.example.xcards.databinding.FragmentRemindersBinding
import java.util.Calendar

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

        binding.cardViewHours.setOnClickListener {
            showTimePicker()
        }

        binding.CardViewMinutes.setOnClickListener {
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
                binding.hoursText.text = hourOfDay.toString()
                binding.minutesText.text = minute.toString()
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