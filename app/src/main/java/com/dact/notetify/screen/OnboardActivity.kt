package com.dact.notetify.screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.dact.notetify.MainActivity
import com.dact.notetify.R

class OnboardActivity : AppCompatActivity() {
    private lateinit var buttonOnboard: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)

        initVar()

        buttonOnboard.setOnClickListener{
            finish()
        }
    }

    private fun initVar(){
        buttonOnboard = findViewById(R.id.buttonOnboard)
    }

}