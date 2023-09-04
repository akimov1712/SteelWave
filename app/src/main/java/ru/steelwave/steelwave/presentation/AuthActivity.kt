package ru.steelwave.steelwave.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}