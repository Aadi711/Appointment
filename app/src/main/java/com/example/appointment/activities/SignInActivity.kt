package com.example.appointment.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.appointment.databinding.ActivitySignInBinding
import com.example.appointment.model.ResponseModel
import com.example.appointment.model.User
import com.example.appointment.utils.Common
import com.example.appointment.utils.Constants
import com.example.example.appointment.activities.BaseActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : BaseActivity() {

    val context = this@SignInActivity
    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        binding.signInButton.setOnClickListener {
            onClickButton()
        }
        binding.layoutDontHaveAccount.setOnClickListener {
            startActivity(SignUpActivity.getIntent(context))
            onBackPressed()
        }
        binding.forgetPasswordText.setOnClickListener {
            startActivity(ForgotPasswordActivity.getIntent(context))
        }
    }

    private fun onClickButton() {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (TextUtils.isEmpty(email)) {
            errorView(context, binding.errorLayout, Constants.EMAIL_REQUIRED)
            return
        }

        if (TextUtils.isEmpty(password)) {
            errorView(context, binding.errorLayout, Constants.PASSWORD_REQUIRED)
            return
        }
        signIn(email, password)
    }

    private fun signIn(email: String, password: String) {
        showCustomProgress(context)
        val call = getApiInterface().login(email, password)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                hideCustomProgress()

                if (response.body()!!.success) {
                    successView(context, binding.errorLayout, Constants.USER_SIGN_SUCCESSFULLY)
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
                errorView(
                    context,
                    binding.errorLayout,
                    t.message.toString()
                )
            }
        })
    }

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }

}