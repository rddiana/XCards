package com.example.xcards.presentation.UI.fragments

import android.app.TimePickerDialog
import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.xcards.R
import com.example.xcards.data.Constants.REQUEST_CODE_ENABLE_ADMIN
import com.example.xcards.data.dataClasses.CardDataLite
import com.example.xcards.data.sharedPref.SharedPreference
import com.example.xcards.databinding.FragmentHardRepetitionsBinding
import com.example.xcards.domain.useCases.FirebaseDatabaseUtils
import com.example.xcards.domain.useCases.MyDeviceAdminReceiver
import com.example.xcards.presentation.adapters.AdapterForMiniCardsChangingColor
import java.util.*

class HardRepetitionsFragment : Fragment() {
    private lateinit var binding: FragmentHardRepetitionsBinding
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager

    private lateinit var database: FirebaseDatabaseUtils
    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHardRepetitionsBinding.inflate(layoutInflater)

        database = FirebaseDatabaseUtils(requireContext().applicationContext)
        sharedPreference = SharedPreference(requireContext().applicationContext)

        val gray = resources.getColor(R.color.gray)
        val darkGray = resources.getColor(R.color.dark_gray)
        val black = resources.getColor(R.color.black)
        val lilac = resources.getColor(R.color.lilac)

        val currentNightMode =
            requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        binding.switchForHardRepetitions.isChecked =
            sharedPreference.getValueBoolean("isHardRepetitionTurnOn", false)

        val switchIsChecked = binding.switchForHardRepetitions.isChecked
        if (switchIsChecked) {
//            possibilityOfChoiceDays()
//            coordinateColorBtWithSwitch(black, lilac)
        } else {
//            coordinateColorBtWithSwitch(darkGray, gray)
        }

        // to request device admin permission
        val devicePolicyManager = requireActivity().getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName = ComponentName(requireContext(), MyDeviceAdminReceiver::class.java)
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "some explanation")
        startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN)

        binding.switchForHardRepetitions.setOnCheckedChangeListener { _, isChecked ->
            var newColorForText = darkGray
            var newColorForButtons = gray
            var isEnable = false

            if (isChecked) {
                sharedPreference.updateBooleanValue("isHardRepetitionTurnOn", true)

                when (currentNightMode) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        newColorForText = black
                    }
                    Configuration.UI_MODE_NIGHT_YES -> {
                        newColorForText = resources.getColor(R.color.light_gray)
                    }
                }

//                possibilityOfChoiceDays()

                newColorForButtons = lilac
                isEnable = true
            } else {
                sharedPreference.updateBooleanValue("isHardRepetitionTurnOn", false)
            }
//
//            binding.buttonViewHours.isEnabled = isEnable
//            binding.buttonViewMinutes.isEnabled = isEnable

//            coordinateColorBtWithSwitch(newColorForText, newColorForButtons)
        }
//
//        binding.buttonViewHours.setOnClickListener {
//            showTimePicker()
//        }
//
//        binding.buttonViewMinutes.setOnClickListener {
//            showTimePicker()
//        }

        staggeredGridLayoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        binding.recyclerViewForMiniCards.layoutManager = staggeredGridLayoutManager

        database.getAllCardsInfo {
            val cardDataLiteArray = arrayListOf<CardDataLite>()
            val normalColor = Integer.toHexString(resources.getColor(R.color.pale_gray_green))

            it.forEach { cardData ->
                cardDataLiteArray.add(CardDataLite(cardData.nameModule, normalColor))
            }

            binding.recyclerViewForMiniCards.adapter = AdapterForMiniCardsChangingColor(
                requireContext(),
                requireContext().applicationContext,
                ArrayList(it),
                cardDataLiteArray,
                R.layout.mini_card,
                normalColor,
                Integer.toHexString(resources.getColor(R.color.gray_green))
            )
        }

        binding.turnBackButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(
                R.id.mainFragmentContainer, SettingFragment()
            ).commit()
        }

        return binding.root
    }

//    private fun possibilityOfChoiceDays() {
//        binding.MondayCardView.setOnClickListener { chooseDayOfWeek(binding.textMonday) }
//        binding.TuesdayCardView.setOnClickListener { chooseDayOfWeek(binding.textTuesday) }
//        binding.WednesdayCardView.setOnClickListener { chooseDayOfWeek(binding.textWednesday) }
//        binding.ThursdayCardView.setOnClickListener { chooseDayOfWeek(binding.textThursday) }
//        binding.FridayCardView.setOnClickListener { chooseDayOfWeek(binding.textFriday) }
//        binding.SaturdayCardView.setOnClickListener { chooseDayOfWeek(binding.textSaturday) }
//        binding.SundayCardView.setOnClickListener { chooseDayOfWeek(binding.textSunday) }
//    }

//    private fun coordinateColorBtWithSwitch(newColorForText: Int, newColorForButtons: Int) {
//        setBgColor(
//            listOf(
//                binding.viewLine1,
//                binding.viewLine2,
//                binding.viewLine3
//            ), newColorForText
//        )
//
//        setTextColor(
//            listOf(
//                binding.settingTime,
//                binding.settingDaysOfWeek,
//                binding.settingDaysOfWeekAdvise,
//                binding.choiceTrainingModule
//            ), newColorForText
//        )
//
//        setBgColor(
//            listOf(
//                binding.buttonViewHours,
//                binding.buttonViewMinutes,
//                binding.textMonday,
//                binding.textTuesday,
//                binding.textWednesday,
//                binding.textThursday,
//                binding.textFriday,
//                binding.textSaturday,
//                binding.textSunday
//            ), newColorForButtons
//        )
//    }

    private fun chooseDayOfWeek(view: TextView) {
        if ((view.background as ColorDrawable).color == resources.getColor(R.color.gray)) {
            view.setBackgroundColor(resources.getColor(R.color.lilac))
//            daysOfWeekArray.add(view)
        } else {
            view.setBackgroundColor(resources.getColor(R.color.gray))
//            daysOfWeekArray.remove(view)
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
//                binding.buttonViewHours.text = hourOfDay.toString()
//                binding.buttonViewMinutes.text = minute.toString()
            },
            hours,
            minutes,
            false
        )

        timePickerDialog.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}