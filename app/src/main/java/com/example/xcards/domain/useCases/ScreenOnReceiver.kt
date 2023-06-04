package com.example.xcards.domain.useCases

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.xcards.data.Constants

class ScreenOnReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(Intent.ACTION_SCREEN_ON)) {
            val launchIntent = context.packageManager?.getLaunchIntentForPackage("com.example.xcards")
            launchIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            launchIntent?.putExtra(Constants.MESSAGE_LAUNCH_INTENT, "from launch intent")
            context.startActivity(launchIntent)
        }
    }
}