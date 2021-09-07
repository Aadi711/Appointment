package com.example.appointment.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.widget.Toast
import com.example.appointment.databinding.ActivitySignInBinding
import com.example.appointment.databinding.ActivitySignUpBinding
import com.example.appointment.model.ResponseModel
import com.example.appointment.model.User
import com.example.appointment.utils.Common
import com.example.appointment.utils.Constants
import com.example.example.appointment.activities.BaseActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {
    val context = this@SignUpActivity
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

    }

    private fun initViews() {
        setRadioButtonText()
        binding.customToolbar.toolbarText.text = ""
        binding.customToolbar.backArrowLayout.setOnClickListener {
            onBackPressed()
        }
        binding.signupButton.setOnClickListener {
            onClickButton()
        }
        binding.layoutAlreadyHaveAccount.setOnClickListener {
            startActivity(SignInActivity.getIntent(context))
            onBackPressed()
        }
    }

    private fun onClickButton() {
        val username = binding.nameText.text.toString()
        val email = binding.emailText.text.toString()
        val phone = binding.phoneText.text.toString()
        val password = binding.passwordText.text.toString()
        if (TextUtils.isEmpty(username)) {
            errorView(context, binding.errorLayout, Constants.USERNAME_REQUIRED)
            return
        }
        if (TextUtils.isEmpty(email)) {
            errorView(context, binding.errorLayout, Constants.EMAIL_REQUIRED)
            return
        }
        if (TextUtils.isEmpty(phone)) {
            errorView(context, binding.errorLayout, Constants.PHONE_REQUIRED)
            return
        }
        if (TextUtils.isEmpty(password)) {
            errorView(context, binding.errorLayout, Constants.PASSWORD_REQUIRED)
            return
        }
        signUp(username, email, phone, password)
    }

    private fun signUp(username: String, email: String, phone: String, password: String) {
        showCustomProgress(context)
        val call = getApiInterface().signup(username, email, phone, password)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                hideCustomProgress()

                if (response.body()!!.success) {
                    successView(context, binding.errorLayout, Constants.ACCOUNT_CREATED)
                    val data = getResponseData(response.body(), context,binding.errorLayout)

                    if (data != null) {
                        try {
                            val gson = Gson()
                            val json = gson.toJson(data)
                            val user = gson.fromJson(json, User::class.java)
                            Common.addUserToSharedPreferences(context, user)
                            startActivity(MainActivity.getIntent(context))
                            onBackPressed()
                        } catch (e: Exception) {

                            e.printStackTrace()
                            errorView(context, binding.errorLayout, e.printStackTrace().toString())
                        }
                    }
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

    private fun setRadioButtonText() {
        binding.termsConditionsRadioButton.text = Html.fromHtml(
            "<font color='#717177'>By Signing up, you agree to the </font>"
                    + "<b>Terms of Service </b>" + "<font color='#717177'>and </font> " + "<b>Privacy Policy</b>"
        )

    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }
}