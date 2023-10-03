package ru.steelwave.steelwave.presentation

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.*
import ru.steelwave.steelwave.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            RQ_PERMISSIONS_FROM_SPLASH_ACTIVITY
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            RQ_PERMISSIONS_FROM_SPLASH_ACTIVITY -> {
                Intent(this@SplashActivity, AuthActivity::class.java).also{
                    startActivity(it)
                    finish()
                }
            }
        }
    }

    companion object{
        private const val RQ_PERMISSIONS_FROM_SPLASH_ACTIVITY = 100
    }

}