package com.example.appointment.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.example.appointment.databinding.ActivityForgotPasswordBinding
import com.example.appointment.model.ResponseModel
import com.example.appointment.utils.Constants
import com.example.example.appointment.activities.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : BaseActivity() {
    val context = this@ForgotPasswordActivity
    lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        binding.customToolbar.toolbarText.text = ""
        binding.customToolbar.backArrowLayout.setOnClickListener {
            onBackPressed()
        }
        binding.nextButton.setOnClickListener {
            onButtonClick()
        }
    }

    private fun onButtonClick() {
        val email = binding.emailText.text.toString()
        if (TextUtils.isEmpty(email)) {
            errorView(context, binding.errorLayout, Constants.EMAIL_REQUIRED)
            return
        }
        sendEmail(email)
    }

    private fun sendEmail(email: String) {
        showCustomProgress(context)
        val call = getApiInterface().sendEmailCode(
            Constants.CLIENT_SERVICE,
            Constants.AUTH_KEY,
            email
        )
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                hideCustomProgress()

                if (response.body()!!.success) {
                    successView(context, binding.errorLayout, response.body()!!.foundMessageCapital)
                    startActivity(OtpActivity.getIntent(context,email))

                } else {
                    errorView(
                        context,
                        binding.errorLayout,
                        response.body()!!.foundMessage.toString()
                    )

                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                hideCustomProgress()

            }
        })

    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, ForgotPasswordActivity::class.java)
        }
    }
}