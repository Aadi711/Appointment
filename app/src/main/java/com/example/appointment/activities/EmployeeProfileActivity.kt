package com.example.appointment.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.appointment.adapters.TimeAdapter
import com.example.appointment.databinding.ActivityEmployeeProfileBinding
import com.example.appointment.model.*
import com.example.appointment.utils.Common
import com.example.appointment.utils.Constants
import com.example.example.appointment.activities.BaseActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class EmployeeProfileActivity : BaseActivity() {
    val context = this@EmployeeProfileActivity
    lateinit var binding: ActivityEmployeeProfileBinding
    lateinit var employee: Employee
    private val TAG = "EmployeeProfileActivity"
    lateinit var date: String
    lateinit var adapter : TimeAdapter
    lateinit var appointmentTime : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        checkRunTimePermissions()
        employee = intent.getSerializableExtra(Constants.EMPLOYEE) as Employee
        setEmployeeProfile(employee)

        binding.customToolbar.toolbarText.text = "Profile"
        binding.customToolbar.backArrowLayout.setOnClickListener {
            onBackPressed()
        }
        initGridRecyclerView(binding.recyclerView, context, 3)


        date = binding.calendarView.date.toString()

        getDates(date)
        binding.makeAppointmentButton.setOnClickListener {
            onAppointmentClick()
        }
        binding.phoneIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL)

            intent.data = Uri.parse("tel:" + binding.employeePhone.text.toString())
            context.startActivity(intent)
        }

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = java.lang.String.format("%d-%02d-%02d ", year, month + 1, dayOfMonth)
            getDates(date)
        }

    }

    private fun onAppointmentClick() {
        if(adapter != null){
            if(adapter.getSelected() == null){
                errorView(context,binding.errorLayout,Constants.TIME_SLOT_REQUIRED)
                return
            }else{
                appointmentTime = adapter.getSelected()!!.time
            }
        }
        if(TextUtils.isEmpty(date)){
            errorView(context,binding.errorLayout,Constants.DATE_REQUIRED)
            return
        }

        startActivity(ConfirmationActivity.getIntent(context,employee,date,appointmentTime))
    }

    private fun getDates(date: String) {
        showCustomProgress(context)
        val call =
            getApiInterface().employeeProfile(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
                employee.id.toString(),
                date
            )

        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(
                call: Call<ResponseModel>,
                t: Throwable
            ) {
                hideCustomProgress()
                errorView(context, binding.errorLayout, t.message.toString())
            }

            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                hideCustomProgress()
                binding.errorLayout.removeAllViews()

                val data = getResponseData(response.body(), context,binding.errorLayout)
                if (data != null) {
                    try {
                        val gson = Gson()
                        val json = gson.toJson(data)

                        val employee =
                            gson.fromJson(json, Employee::class.java)
                        if (employee.employeeDetail != null) {
                            initData(employee.employeeDetail)

                        }
                    } catch (e: Exception) {
                        showClosedScreen()
                        e.printStackTrace()
                    }
                }else{
                    showClosedScreen()

                }
            }
        })


    }

    private fun initData(employeeDetail: EmployeeDetail) {
        val timesList: ArrayList<Appointment> = ArrayList()
        //setEmployeeProfile(employeeDetail)
        if (employeeDetail.status.lowercase() == "close" || employeeDetail.status.lowercase() == "closed") {
            showClosedScreen()
        } else {
            binding.textViewClosed.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            val startTime = employeeDetail.startTime
            val endTime = employeeDetail.endTime
            val timeGap = employeeDetail.timeGap

            var dateTimeStart = Common.timeFormat.parseLocalTime(startTime)
            val dateTimeEnd = Common.timeFormat.parseLocalTime(endTime)

            do {
                timesList.add(
                    Appointment(
                        Common.timeFormat.print(dateTimeStart) + " to " + Common.timeFormat.print(
                            dateTimeStart.plusMinutes(timeGap.toInt())
                        ), "not booked"
                    )
                )
                dateTimeStart = dateTimeStart.plusMinutes(timeGap.toInt())

            } while (dateTimeStart.isBefore(dateTimeEnd))
//        setRecyclerViewAdapter(timesList)

            if (employeeDetail.bookedTimeSlots != null) {
                var bookedTimeSlotsList: ArrayList<Time> = ArrayList()
                bookedTimeSlotsList = employeeDetail.bookedTimeSlots.timeList
                timesList.forEach { time ->
                    bookedTimeSlotsList.forEach { bookedTime ->
                        if (time.time.lowercase() == bookedTime.time.lowercase()) {
                            time.status = "booked"
                        }
                    }
                }
              /*  timesList.forEach { time ->
                        if (time.time.lowercase() == "07:00 AM to 07:15 AM".lowercase()) {
                            time.status = Constants.BOOKED
                        }
                    }*/
            }
            setRecyclerViewAdapter(timesList)
        }
    }

    private fun setEmployeeProfile(employeeDetail: EmployeeDetail) {
        Glide.with(context).load(employeeDetail.image).into(binding.employeeImage)
        binding.employeeName.text = employeeDetail.name
        binding.employeeEmail.text = employeeDetail.email
        binding.employeePhone.text = employeeDetail.phone

    }

    private fun setEmployeeProfile(employee: Employee) {
        Glide.with(context).load(employee.image).into(binding.employeeImage)
        binding.employeeName.text = employee.name
        binding.employeeEmail.text = employee.email
        binding.employeePhone.text = employee.phone

    }

    private fun setRecyclerViewAdapter(timesList: ArrayList<Appointment>) {
        adapter = object : TimeAdapter(context, timesList) {
            override fun onClickItem(appointment: Appointment) {
            }

        }
        binding.recyclerView.adapter = adapter
    }


    companion object {
        fun getIntent(context: Context, employee: Employee): Intent {
            return Intent(context, EmployeeProfileActivity::class.java)
                .putExtra(Constants.EMPLOYEE, employee)
        }
    }

    private fun showClosedScreen(){
        binding.textViewClosed.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.textViewClosed.text = "Today " + binding.employeeName.text.toString() + " is on leave"
    }
    private fun checkRunTimePermissions() {
        if (ActivityCompat.checkSelfPermission(this , Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this , arrayOf(Manifest.permission.CALL_PHONE) , 1)
            return
        }
    }
}