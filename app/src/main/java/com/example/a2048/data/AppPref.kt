package com.example.a2048.data

import android.content.Context
import android.content.SharedPreferences

class AppPref private constructor() {
    companion object {
        private lateinit var pref: SharedPreferences
        private lateinit var instance: AppPref

        fun init(context: Context) {
            pref = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
            instance = AppPref()
        }

        fun getInstance(): AppPref = instance
    }

    var score:String
       set(value) = pref.edit().putString("Score",value).apply()
       get() = pref.getString("Score","")!!
}
