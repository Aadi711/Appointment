package com.example.appointment.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appointment.databinding.ActivityPrivacyPolicyActivityBinding

class PrivacyPolicyActivity : AppCompatActivity() {
    val context = this@PrivacyPolicyActivity
    lateinit var binding : ActivityPrivacyPolicyActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivacyPolicyActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        binding.customToolbar.toolbarText.text = "Privacy Policy"
        binding.customToolbar.backArrowLayout.setOnClickListener {
            onBackPressed()
        }
    }
    companion object {
        fun getIntent(context: Context?): Intent {
            return Intent(context, PrivacyPolicyActivity::class.java)
        }
    }
}