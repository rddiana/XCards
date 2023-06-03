package com.example.xcards.presentation

import android.app.TimePickerDialog
import android.content.res.Configuration
import android.graphics.Color
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
import com.example.xcards.domain.useCase.SharedPreference
import com.example.xcards.presentation.activities.MainActivity
import java.util.*
import kotlin.collections.ArrayList

class RemindersFragment : Fragment() {
    private lateinit var binding: FragmentRemindersBinding
//    var daysOfWeekArray: ArrayList<Int> = arrayListOf()
    private lateinit var sharedPreference: SharedPreference

    private var currentNightMode: Int = 0

    companion object {
        fun newInstance() = RemindersFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRemindersBinding.inflate(layoutInflater)

        sharedPreference = SharedPreference(requireContext())

        currentNightMode = requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        binding.switchForReminders.isChecked = sharedPreference.getValueBoolean("isNotificationTurnOn", false)

        val gray = resources.getColor(R.color.gray)
        val darkGray = resources.getColor(R.color.dark_gray)
        val black = resources.getColor(R.color.black)
        val lilac = resources.getColor(R.color.lilac)

        val switchIsChecked = binding.switchForReminders.isChecked
        if (switchIsChecked) {
            whenIsChecked(switchIsChecked)

            MainActivity().daysOfWeekArray = arrayListOf(
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.THURSDAY,
                Calendar.FRIDAY,
                Calendar.SATURDAY,
                Calendar.SUNDAY
            )
        } else {
            coordinateColorBtWithSwitch(darkGray, gray)
        }

        binding.switchForReminders.setOnCheckedChangeListener { _, isChecked ->
            whenIsChecked(isChecked)
        }

        binding.buttonViewHours.setOnClickListener {
            showTimePicker()
        }

        binding.buttonViewMinutes.setOnClickListener {
            showTimePicker()
        }

        binding.toPreviousFragment3.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, SettingFragment()).commit()
        }

        return binding.root
    }

    private fun whenIsChecked(isChecked: Boolean) {
        val gray = resources.getColor(R.color.gray)
        val darkGray = resources.getColor(R.color.dark_gray)
        val black = resources.getColor(R.color.black)
        val lilac = resources.getColor(R.color.lilac)

        var newColorForText = darkGray
        var newColorForButtons = gray
        var isEnable = false

        sharedPreference.updateBooleanValue("isNotificationTurnOn", isChecked)

        if (isChecked) {
            MainActivity().daysOfWeekArray = arrayListOf(
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.THURSDAY,
                Calendar.FRIDAY,
                Calendar.SATURDAY,
                Calendar.SUNDAY
            )

            when (currentNightMode) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    newColorForText = black
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    newColorForText = resources.getColor(R.color.light_gray)
                }
            }

            possibilityOfChoiceDays()

            newColorForButtons = lilac
            isEnable = true
        } else {
            MainActivity().daysOfWeekArray.clear()
        }

        binding.buttonViewHours.isEnabled = isEnable
        binding.buttonViewMinutes.isEnabled = isEnable

        coordinateColorBtWithSwitch(newColorForText, newColorForButtons)
    }

    private fun possibilityOfChoiceDays() {
        binding.MondayCardView.setOnClickListener { chooseDayOfWeek(binding.textMonday) }
        binding.TuesdayCardView.setOnClickListener { chooseDayOfWeek(binding.textTuesday) }
        binding.WednesdayCardView.setOnClickListener { chooseDayOfWeek(binding.textWednesday) }
        binding.ThursdayCardView.setOnClickListener { chooseDayOfWeek(binding.textThursday) }
        binding.FridayCardView.setOnClickListener { chooseDayOfWeek(binding.textFriday) }
        binding.SaturdayCardView.setOnClickListener { chooseDayOfWeek(binding.textSaturday) }
        binding.SundayCardView.setOnClickListener { chooseDayOfWeek(binding.textSunday) }
    }

    private fun coordinateColorBtWithSwitch(newColorForText: Int, newColorForButtons: Int) {
        setBgColor(listOf(
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
            binding.buttonViewHours,
            binding.buttonViewMinutes,
            binding.textMonday,
            binding.textTuesday,
            binding.textWednesday,
            binding.textThursday,
            binding.textFriday,
            binding.textSaturday,
            binding.textSunday
        ), newColorForButtons)
    }

    private fun chooseDayOfWeek(view: TextView) {
        var currentDate = 0
        when (view.text) {
            resources.getString(R.string.monday) -> currentDate = Calendar.MONDAY
            resources.getString(R.string.tuesday) -> currentDate = Calendar.TUESDAY
            resources.getString(R.string.wednesday) -> currentDate = Calendar.WEDNESDAY
            resources.getString(R.string.thursday) -> currentDate = Calendar.THURSDAY
            resources.getString(R.string.friday) -> currentDate = Calendar.FRIDAY
            resources.getString(R.string.saturday) -> currentDate = Calendar.SATURDAY
            resources.getString(R.string.sunday) -> currentDate = Calendar.SUNDAY
        }

        if ((view.background as ColorDrawable).color == resources.getColor(R.color.gray)) {
            view.setBackgroundColor(resources.getColor(R.color.lilac))
            MainActivity().daysOfWeekArray.add(currentDate)
        } else {
            view.setBackgroundColor(resources.getColor(R.color.gray))
            MainActivity().daysOfWeekArray.remove(currentDate)
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

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            binding.buttonViewHours.text = hour.toString()
            binding.buttonViewMinutes.text = minute.toString()
        }

        val timePickerDialog = TimePickerDialog(
            context,
            R.style.timePickerStyle,
            timeSetListener,
            hours,
            minutes,
            true
        )

        timePickerDialog.show()
//
//        timePickerDialog.getButton(TimePickerDialog.BUTTON_NEGATIVE)
//            .setBackgroundColor(Color.BLACK)
//
//        timePickerDialog.getButton(TimePickerDialog.BUTTON_POSITIVE)
//            .setBackgroundColor(Color.BLACK)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}