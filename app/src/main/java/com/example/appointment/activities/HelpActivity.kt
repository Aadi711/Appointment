package com.example.appointment.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.appointment.databinding.ActivityHelpBinding
import com.example.appointment.model.ResponseModel
import com.example.appointment.utils.Common
import com.example.appointment.utils.Constants
import com.example.example.appointment.activities.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HelpActivity : BaseActivity() {
    val context = this@HelpActivity
    lateinit var binding: ActivityHelpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        binding.customToolbar.toolbarText.text = "Help"
        binding.customToolbar.backArrowLayout.setOnClickListener {
            onBackPressed()
        }
        binding.descriptionButton.setOnClickListener {
            onButtonClick()
        }
    }

    private fun onButtonClick() {
        val email = binding.emailText.text.toString()
        val description = binding.description.text.toString()
        if (TextUtils.isEmpty(email)) {
            errorView(context, binding.errorLayout, Constants.EMAIL_REQUIRED)
            return
        }
        if (TextUtils.isEmpty(description)) {
            errorView(context, binding.errorLayout, Constants.DESCRIPTION_REQUIRED)
            return
        }


        sendEmail(email, description)
    }

    private fun sendEmail(email: String,message: String) {
        showCustomProgress(context)
        val call =
            getApiInterface().help(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
                email,
                message
            )

        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(
                call: Call<ResponseModel>,
                t: Throwable
            ) {
                hideCustomProgress()

            }

            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                hideCustomProgress()

                if (response.body()!!.success) {
                    successView(context, binding.errorLayout, response.body()!!.foundMessage)
                    onBackPressed()
                } else {
                    errorView(context, binding.errorLayout, response.body()!!.foundMessage)
                }
            }
        })

    }

    companion object {
        fun getIntent(context: Context?): Intent {
            return Intent(context, HelpActivity::class.java)
        }
    }
}