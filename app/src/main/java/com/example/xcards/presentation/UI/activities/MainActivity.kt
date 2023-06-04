package com.example.xcards.presentation.UI.activities

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.xcards.R
import com.example.xcards.data.Constants
import com.example.xcards.data.sharedPref.SharedPreference
import com.example.xcards.databinding.ActivityMainBinding
import com.example.xcards.domain.useCases.*
import com.example.xcards.presentation.UI.fragments.*
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var sharedPreference: SharedPreference
    private lateinit var database: FirebaseDatabaseUtils

    private lateinit var fragmentManager: FragmentManager
    private lateinit var broadcastReceiver: BroadcastReceiver

    private var startTime = -1L

    var daysOfWeekArray = arrayListOf<Int>()
    private lateinit var message: String

    override fun onStart() {
        super.onStart()
        startTime = System.currentTimeMillis()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        fragmentManager = supportFragmentManager
        sharedPreference = SharedPreference(applicationContext)
        database = FirebaseDatabaseUtils(applicationContext)

        val isHardRepetitionTurnOn =
            sharedPreference.getValueBoolean("isHardRepetitionTurnOn", false)

        if (isHardRepetitionTurnOn) {
            val testName = sharedPreference.getValueString("chosenTestForHR")

            if (intent.getBooleanExtra("isHardRepetitionRequired", false)) {
                if (testName != null) {
                    database.getCardsCollection(testName) {
                        fragmentManager.beginTransaction().replace(
                            R.id.mainFragmentContainer, DisplayingCardsFragment(it)
                        ).commit()
                    }
                }
            }
        }

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        checkUser()

        turnButtonNavOn(binding.toHomeFragment)

        binding.toChartFragment.setOnClickListener {
            changeFragment(binding.toChartFragment)
            turnButtonNavOn(binding.toChartFragment)
            turnButtonNavOff(binding.toHomeFragment)
            turnButtonNavOff(binding.toProfileFragment)
        }

        binding.toHomeFragment.setOnClickListener {
            changeFragment(binding.toHomeFragment)
            turnButtonNavOn(binding.toHomeFragment)
            turnButtonNavOff(binding.toChartFragment)
            turnButtonNavOff(binding.toProfileFragment)
        }

        binding.toProfileFragment.setOnClickListener {
            changeFragment(binding.toProfileFragment)
            turnButtonNavOn(binding.toProfileFragment)
            turnButtonNavOff(binding.toHomeFragment)
            turnButtonNavOff(binding.toChartFragment)
        }

        if (sharedPreference.getValueBoolean("isNotificationTurnOn", false)) {
            daysOfWeekArray.forEach { dayOfWeek ->
                createNotificationChannel()
                scheduleNotification(dayOfWeek)
            }
        }
    }

    private fun scheduleNotification(dayOfWeek: Int) {
        val intent = Intent(applicationContext, NotificationUtils::class.java)
        val title = "title"
        val message = "message"

        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime(dayOfWeek)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
//        showAlert(time, title, message)
    }

//    private fun showAlert(time: Long, title: String, message: String) {
//        val date = Date(time)
//        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
//        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)
//
//        AlertDialog.Builder(this)
//            .setTitle(R.string.notification_title)
//            .setMessage(R.string.motivation_phrase)
//            .setPositiveButton("Okay") {_, _ ->}
//            .show()
//    }

    //!!
    private fun getTime(dayOfWeek: Int): Long {
        val hours = findViewById<MaterialButton>(R.id.buttonViewHours).text.toString().toInt()
        val minutes = findViewById<MaterialButton>(R.id.buttonViewMinutes).text.toString().toInt()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val calendar = Calendar.getInstance()
        calendar.set(
            currentDate.substring(5, 9).toInt(),
            currentDate.substring(3, 4).toInt(),
            currentDate.substring(0, 3).toInt(),
            hours,
            minutes
        )
        calendar.setWeekDate(
            52,
            Calendar.WEEK_OF_YEAR,
            dayOfWeek
        )
        return calendar.timeInMillis
    }

    private fun createNotificationChannel() {
        val name = "Notif channel"
        val desc = "A Description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onStop() {
        super.onStop()

//        val timeBefore = sharedPreference.getValueFloat("time")
//        val resultTimeMinutes = ((System.currentTimeMillis() - startTime).toFloat() / 1000 / 60).toInt() + timeBefore
//        sharedPreference.updateFloatValue("time", resultTimeMinutes)
    }


    private fun turnButtonNavOn(cardView: CardView) {
        val cardColor = ContextCompat.getColor(this, R.color.sky_blue)
        cardView.setCardBackgroundColor(cardColor)
    }

    private fun turnButtonNavOff(cardView: CardView) {
        val cardColor = ContextCompat.getColor(this, R.color.transparent)
        cardView.setCardBackgroundColor(cardColor)
    }

    private fun changeFragment(cardView: CardView) {
        var fragment = Fragment()

        when (cardView) {
            binding.toChartFragment -> fragment = ChartFragment()
            binding.toHomeFragment -> fragment = HomeFragment()
            binding.toProfileFragment -> fragment = ProfileFragment()
        }

        fragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, fragment).commit()
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, OnboardingActivity::class.java))
            finish()
        }
    }
}