package com.example.a2048.mvp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.a2048.R

class InfoScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_screen)

        val back=findViewById<ImageView>(R.id.backButtton)

        back.setOnClickListener{
            finish()
        }
    }
}