package com.example.xcards.presentation.activities

import android.content.BroadcastReceiver
import android.content.Intent
import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.xcards.R
import com.example.xcards.databinding.ActivityMainBinding
import com.example.xcards.domain.useCase.SharedPreference
import com.example.xcards.presentation.ChartFragment
import com.example.xcards.presentation.HomeFragment
import com.example.xcards.presentation.ProfileFragment
import com.example.xcards.presentation.SettingFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreference: SharedPreference

    private lateinit var fragmentManager: FragmentManager

    private lateinit var broadcastReceiver: BroadcastReceiver

    private val _secondsUpTime = MutableLiveData<Int>()
    val secondsUpTime: LiveData<Int> = _secondsUpTime

    private var startTime = -1L
    private var counterJob: Job? = null

    override fun onStart() {
        super.onStart()

        startTime = System.currentTimeMillis()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        sharedPreference = SharedPreference(applicationContext)

        checkUser()

        fragmentManager = supportFragmentManager
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
    }

    override fun onStop() {
        super.onStop()

        val timeBefore = (sharedPreference.getValueString("time")?.toFloat()?: "0").toString().toFloat()
        val resultTimeMinutes = ((System.currentTimeMillis() - startTime) / 1000 / 60).toInt() + timeBefore
        sharedPreference.save("time", resultTimeMinutes.toString())
    }


    private fun turnButtonNavOn(cardView: CardView) {
        var cardColor = ContextCompat.getColor(this, R.color.sky_blue)
        cardView.setCardBackgroundColor(cardColor)
    }

    private fun turnButtonNavOff(cardView: CardView) {
        var cardColor = ContextCompat.getColor(this, R.color.transparent)
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

    private fun itemSelectedListenerForSpinner() {

    }
}