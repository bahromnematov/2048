package com.example.a2048.app

import android.app.Application
import com.example.a2048.data.AppPref

class App :Application(){

    override fun onCreate() {
        super.onCreate()
        AppPref.init(this)
    }
}