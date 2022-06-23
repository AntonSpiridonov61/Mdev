package com.example.subscriberapp

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    val receiver = MyReceiver()
    val intentFilterAvia = IntentFilter("android.intent.action.AIRPLANE_MODE")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(receiver, intentFilterAvia)
        registerReceiver(receiver, IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}