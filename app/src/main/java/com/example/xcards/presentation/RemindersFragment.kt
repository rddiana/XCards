package com.example.xcards.presentation

import android.app.TimePickerDialog
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.xcards.R
import com.example.xcards.databinding.FragmentRemindersBinding
import java.util.*
import kotlin.collections.ArrayList


class RemindersFragment : Fragment() {
    private lateinit var binding: FragmentRemindersBinding
    private lateinit var daysOfWeekArray: ArrayList<TextView>

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

            setBgColor(listOf(
                binding.buttonViewHours,
                binding.buttonViewMinutes,
                binding.viewLine1,
                binding.viewLine2
            ), newColorForText)

            setTextColor(listOf(
                binding.settingTime,
                binding.settingTimeAdvise,
                binding.settingDaysOfWeek,
                binding.settingDaysOfWeekAdvise
            ), newColorForText)

            setBgColor(listOf(
                binding.textMonday,
                binding.textTuesday,
                binding.textWednesday,
                binding.textThursday,
                binding.textFriday,
                binding.textSaturday,
                binding.textSunday
            ), newColorForButtons)

            if (isChecked) {
                binding.MondayCardView.setOnClickListener { chooseDayOfWeek(binding.textMonday) }
                binding.TuesdayCardView.setOnClickListener { chooseDayOfWeek(binding.textTuesday) }
                binding.WednesdayCardView.setOnClickListener { chooseDayOfWeek(binding.textWednesday) }
                binding.ThursdayCardView.setOnClickListener { chooseDayOfWeek(binding.textThursday) }
                binding.FridayCardView.setOnClickListener { chooseDayOfWeek(binding.textFriday) }
                binding.SaturdayCardView.setOnClickListener { chooseDayOfWeek(binding.textSaturday) }
                binding.SundayCardView.setOnClickListener { chooseDayOfWeek(binding.textSunday) }
            }
        }

        binding.buttonViewHours.setOnClickListener {
            showTimePicker()
        }

        binding.buttonViewMinutes.setOnClickListener {
            showTimePicker()
        }

        return binding.root
    }

    private fun chooseDayOfWeek(view: TextView) {
        if ((view.background as ColorDrawable).color == resources.getColor(R.color.gray)) {
            view.setBackgroundColor(resources.getColor(R.color.lilac))
            daysOfWeekArray.add(view)
        } else {
            view.setBackgroundColor(resources.getColor(R.color.gray))
            daysOfWeekArray.remove(view)
        }
    }

    private fun setTextColor(textViews: List<TextView>, color: Int) {
        for (card in textViews) {
            card.setTextColor(color)
        }
    }

    private fun setBgColor(textViews: List<View>, color: Int) {
        for (card in textViews) {
            card.setBackgroundColor(color)
        }
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