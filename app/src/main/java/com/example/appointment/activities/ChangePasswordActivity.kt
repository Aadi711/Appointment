package com.example.appointment.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.appointment.databinding.ActivityChangePasswordBinding
import com.example.appointment.model.ResponseModel
import com.example.appointment.utils.Constants
import com.example.example.appointment.activities.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : BaseActivity() {
    val context = this@ChangePasswordActivity
    lateinit var binding: ActivityChangePasswordBinding
    lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.customToolbar.toolbarText.text = ""
        email = intent.getStringExtra(Constants.EMAIL).toString()
        binding.customToolbar.backArrowLayout.setOnClickListener {
            onBackPressed()
        }
        binding.changeButton.setOnClickListener {
            onButtonClick()
        }
    }

    private fun onButtonClick() {
        val password = binding.passwordText.text.toString()
        if (TextUtils.isEmpty(password)) {
            errorView(context, binding.errorLayout, Constants.PASSWORD_REQUIRED)
            return
        }
        resetPassword(password)
    }

    private fun resetPassword(password: String) {
        showCustomProgress(context)
        val call =
            getApiInterface().resetPassword(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
                email,
                password
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
                    if (response.body()!!.success) {
                        successView(context, binding.errorLayout, response.body()!!.foundMessage)
                        startActivity(SignInActivity.getIntent(context))
                        onBackPressed()
                    } else {
                        errorView(context, binding.errorLayout, response.body()!!.foundMessage)
                    }
                } catch (e: Exception) {

                    e.printStackTrace()
                }

            }

        })
    }

    companion object {
        fun getIntent(context: Context, email: String): Intent {
            return Intent(context, ChangePasswordActivity::class.java)
                .putExtra(Constants.EMAIL, email)
        }
    }

}