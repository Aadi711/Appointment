package com.example.appointment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.example.appointment.databinding.ActivitySplashBinding
import com.example.appointment.utils.Common
import com.example.appointment.utils.PreferencesManager

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding
    val context = this@SplashActivity
    private val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        PreferencesManager.init(context)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Handler().postDelayed({
            val user = Common.getUserFromSharedPreferences(context)
            if (user != null) {
               startActivity(MainActivity.getIntent(context))
            } else {
                startActivity(SignInActivity.getIntent(context))
                finish()
            }
        }, 3000)
    }
}