package ru.steelwave.steelwave.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import ru.steelwave.steelwave.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            Intent(this@SplashActivity, AuthActivity::class.java).also{
                startActivity(it)
                finish()
            }
        }
    }
}