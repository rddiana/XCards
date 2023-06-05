package com.example.xcards.domain.useCases

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.xcards.data.Constants
import com.example.xcards.presentation.UI.activities.MainActivity

class ScreenOnBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_SCREEN_ON)) {
            val launchIntent = Intent(context, MainActivity::class.java)
            launchIntent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            launchIntent.action = Intent.ACTION_MAIN
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            val bundle = Bundle()
            bundle.putBoolean("isHardRepetitionRequired", true)
            launchIntent.putExtras(bundle)
            context?.startActivity(launchIntent)
        }
    }
}