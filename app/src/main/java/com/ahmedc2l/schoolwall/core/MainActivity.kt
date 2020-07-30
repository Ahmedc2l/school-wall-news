package com.ahmedc2l.schoolwall.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ahmedc2l.schoolwall.R
import com.ahmedc2l.schoolwall.core.network.Network
import kotlinx.coroutines.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}