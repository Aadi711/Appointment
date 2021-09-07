package com.example.appointment.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.appointment.R
import com.example.appointment.adapters.AppointmentAdapter
import com.example.appointment.databinding.FragmentAppointmentBinding
import com.example.appointment.model.Appointment
import com.example.appointment.model.Employee
import com.example.appointment.model.ResponseModel
import com.example.appointment.model.Result
import com.example.appointment.utils.Constants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentFragment : BaseFragment() {

    lateinit var binding: FragmentAppointmentBinding
    private lateinit var appointmentList: ArrayList<Appointment>
    private var previousClicked = false
    private var todayClicked = false
    private var upcomingClicked = false
    private val TAG = "AppointmentFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        initRecyclerView(binding.recyclerView, context)
        defaultSelectedButton()
        buttonClicks()
        getAppointment(Constants.TODAY)
        binding.swipeRefreshLayout.setOnRefreshListener {
            when {
                todayClicked -> {
                    getAppointment(Constants.TODAY)
                }
                previousClicked -> {
                    getAppointment(Constants.PREVIOUS)

                }
                upcomingClicked -> {
                    getAppointment(Constants.UPCOMING)
                }
            }
        }
    }

    private fun getAppointment(appointmentName: String) {
        appointmentList = ArrayList()
        binding.swipeRefreshLayout.isRefreshing = true

        val call =
            getApiInterface().getAppointments(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
//                employee.id.toString(),
                "1",
                appointmentName
            )

        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(
                call: Call<ResponseModel>,
                t: Throwable
            ) {
                binding.swipeRefreshLayout.isRefreshing = false

            }

            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                binding.swipeRefreshLayout.isRefreshing = false


                val data = getResponseData(response.body(), context)
                if (data != null) {
                    try {
                        val gson = Gson()
                        val json = gson.toJson(data)
                        val result =
                            gson.fromJson(json, Result::class.java)
                        if(result.result != null && result.result.size > 0){
                            setRecyclerViewAdapter(result.result)
                        }
                    } catch (e: Exception) {

                        e.printStackTrace()
                    }
                }
            }
        })
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun setRecyclerViewAdapter(appointmentList: ArrayList<Result>) {
        val adapter = object : AppointmentAdapter(context, appointmentList) {
            override fun onClickItem(result: Result) {

            }

        }
        binding.recyclerView.adapter = adapter
    }

    private fun buttonClicks() {
        binding.previousButton.setOnClickListener {
            binding.previousButton.backgroundTintList =
                ActivityCompat.getColorStateList(requireContext(), R.color.text_blue_color)
            binding.previousButton.setTextColor(
                ActivityCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )

            binding.todayButton.backgroundTintList =
                ActivityCompat.getColorStateList(
                    requireContext(),
                    R.color.tabs_item_background_default_color
                )
            binding.todayButton.setTextColor(
                ActivityCompat.getColor(
                    requireContext(),
                    R.color.tabs_item_default_color
                )
            )


            binding.upcomingButton.backgroundTintList =
                ActivityCompat.getColorStateList(
                    requireContext(),
                    R.color.tabs_item_background_default_color
                )
            binding.upcomingButton.setTextColor(
                ActivityCompat.getColor(
                    requireContext(),
                    R.color.tabs_item_default_color
                )
            )

            previousClicked = true
            todayClicked = false
            upcomingClicked = false
        }
        binding.todayButton.setOnClickListener {
            binding.todayButton.backgroundTintList =
                ActivityCompat.getColorStateList(requireContext(), R.color.text_blue_color)
            binding.todayButton.setTextColor(
                ActivityCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )


            binding.previousButton.backgroundTintList =
                ActivityCompat.getColorStateList(
                    requireContext(),
                    R.color.tabs_item_background_default_color
                )
            binding.previousButton.setTextColor(
                ActivityCompat.getColor(
                    requireContext(),
                    R.color.tabs_item_default_color
                )
            )


            binding.upcomingButton.backgroundTintList =
                ActivityCompat.getColorStateList(
                    requireContext(),
                    R.color.tabs_item_background_default_color
                )
            binding.upcomingButton.setTextColor(
                ActivityCompat.getColor(
                    requireContext(),
                    R.color.tabs_item_default_color
                )
            )

            previousClicked = false
            todayClicked = true
            upcomingClicked = false
        }
        binding.upcomingButton.setOnClickListener {
            binding.upcomingButton.backgroundTintList =
                ActivityCompat.getColorStateList(requireContext(), R.color.text_blue_color)
            binding.upcomingButton.setTextColor(
                ActivityCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )


            binding.todayButton.backgroundTintList =
                ActivityCompat.getColorStateList(
                    requireContext(),
                    R.color.tabs_item_background_default_color
                )
            binding.todayButton.setTextColor(
                ActivityCompat.getColor(
                    requireContext(),
                    R.color.tabs_item_default_color
                )
            )


            binding.previousButton.backgroundTintList =
                ActivityCompat.getColorStateList(
                    requireContext(),
                    R.color.tabs_item_background_default_color
                )
            binding.previousButton.setTextColor(
                ActivityCompat.getColor(
                    requireContext(),
                    R.color.tabs_item_default_color
                )
            )

            previousClicked = false
            todayClicked = false
            upcomingClicked = true
        }
    }

    private fun defaultSelectedButton() {
        binding.todayButton.backgroundTintList =
            ActivityCompat.getColorStateList(requireContext(), R.color.text_blue_color)
        binding.todayButton.setTextColor(
            ActivityCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        todayClicked = true
        previousClicked = false
        upcomingClicked = false
    }

}