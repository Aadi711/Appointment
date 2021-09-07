package com.example.appointment.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.example.appointment.databinding.ActivityOtpBinding
import com.example.appointment.model.ResponseModel
import com.example.appointment.utils.Constants
import com.example.example.appointment.activities.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpActivity : BaseActivity() {
    val context = this@OtpActivity
    lateinit var binding : ActivityOtpBinding
    lateinit var email : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        email = intent.getStringExtra(Constants.EMAIL).toString()
        binding.customToolbar.toolbarText.text = ""
        binding.customToolbar.backArrowLayout.setOnClickListener {
            onBackPressed()
        }
        binding.otpView.setOtpCompletionListener {

        }
        binding.nextButton.setOnClickListener {
            onButtonClick()
        }
    }
    private fun onButtonClick() {
        val otp = binding.otpView.text.toString()
        if(TextUtils.isEmpty(otp)){
            errorView(context,binding.errorLayout, Constants.OTP_REQUIRED)
            return
        }

        if(otp.length < 4){
            errorView(context,binding.errorLayout,Constants.OTP_COMPLETE_REQUIRED)
            return
        }
        sendOtp(otp)

    }

    private fun sendOtp(otp: String) {
        showCustomProgress(context)
        val call =
            getApiInterface().sendOtpCode(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
                email,
                otp
            )

        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                hideCustomProgress()
                errorView(context, binding.errorLayout, t.message.toString())
            }

            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                hideCustomProgress()
                binding.errorLayout.removeAllViews()
                try {
                    if(response.body()!!.success){
                        successView(context,binding.errorLayout,response.body()!!.foundMessage)
                        startActivity(ChangePasswordActivity.getIntent(context,email))
                    }else{
                        errorView(context,binding.errorLayout,response.body()!!.foundMessage)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        })
    }

    companion object{
        fun getIntent(context: Context, email: String) : Intent{
            return Intent(context,OtpActivity::class.java)
                .putExtra(Constants.EMAIL,email)
        }
    }
}