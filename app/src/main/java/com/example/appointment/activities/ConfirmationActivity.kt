package com.example.appointment.activities

import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.example.appointment.databinding.ActivityConfirmationBinding
import com.example.appointment.model.Employee
import com.example.appointment.model.ResponseModel
import com.example.appointment.model.Result
import com.example.appointment.utils.Common
import com.example.appointment.utils.Constants
import com.example.example.appointment.activities.BaseActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmationActivity : BaseActivity() {
    val context = this@ConfirmationActivity
    lateinit var binding: ActivityConfirmationBinding
    lateinit var employee: Employee
    lateinit var date: String
    lateinit var appointmentTime: String
    private val TAG = "ConfirmationActivity"
    lateinit var description: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.customToolbar.toolbarText.text = "Confirmation"
        employee = intent.getSerializableExtra(Constants.EMPLOYEE) as Employee
        date = intent.getStringExtra(Constants.DATE).toString()
        appointmentTime = intent.getStringExtra(Constants.APPOINTMENT_DETAIL).toString()
        setFields()
        binding.customToolbar.backArrowLayout.setOnClickListener {
            onBackPressed()
        }
        binding.confirmationButton.setOnClickListener {
            onButtonClick()
        }
    }

    private fun onButtonClick() {
        description = binding.description.text.toString()

        if (TextUtils.isEmpty(description)) {
            errorView(context, binding.errorLayout, Constants.DESCRIPTION_REQUIRED)
            return
        }
        makeOrder()
    }

    private fun makeOrder() {

        showCustomProgress(context)
        val call =
            getApiInterface().order(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
                Common.getUserFromSharedPreferences(context).id.toString(),
                employee.shopName,
                employee.name,
                date,
                appointmentTime,
                description
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
                    startActivity(MainActivity.getIntent(context))
                    onBackPressed()
                } else {
                    errorView(context, binding.errorLayout, response.body()!!.foundMessage)
                }
            }
        })
    }

    private fun setFields() {
        binding.shopName.text = employee.shopName
        binding.employeeName.text = employee.name
        binding.date.text = date
        binding.time.text = appointmentTime

    }

    companion object {
        fun getIntent(
            context: Context,
            employee: Employee,
            date: String,
            appointmentTime: String
        ): Intent {
            return Intent(context, ConfirmationActivity::class.java)
                .putExtra(Constants.EMPLOYEE, employee)
                .putExtra(Constants.DATE, date)
                .putExtra(Constants.APPOINTMENT_DETAIL, appointmentTime)
        }
    }
}