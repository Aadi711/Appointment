package com.example.appointment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appointment.adapters.AppointmentTypeAdapter
import com.example.appointment.model.AppointmentType
import com.example.appointment.activities.EmployeeActivity
import com.example.appointment.databinding.FragmentHomeBinding
import com.example.appointment.model.ResponseModel
import com.example.appointment.model.Stores
import com.example.appointment.model.StoresDetail
import com.example.appointment.utils.Constants
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {

    lateinit var binding : FragmentHomeBinding
    lateinit var appointmentTypeList : ArrayList<AppointmentType>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        initRecyclerView(binding.recyclerView, context)
        getAppointments()
        binding.swipeRefreshLayout.setOnRefreshListener {
            getAppointments()
        }
    }

    private fun getAppointments() {
        binding.swipeRefreshLayout.isRefreshing = true
        val call =
            getApiInterface().stores(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
                "dummyemail@gmail.com"
            )

        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(
                call: Call<ResponseModel>,
                t: Throwable
            ) {
                binding.swipeRefreshLayout.isRefreshing = false
                errorView(context!!, binding.errorLayout, t.message.toString())
            }

            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.errorLayout.removeAllViews()

                val data = getResponseData(response.body(), context)
                if (data != null) {
                    try {
                        val gson = Gson()
                        val json = gson.toJson(data)

                        val stores =
                            gson.fromJson(json, Stores::class.java)
                        initData(stores.storesDetails)
                    } catch (e: Exception) {
                        binding.swipeRefreshLayout.isRefreshing = false

                        e.printStackTrace()
                    }
                }
            }
        })
    }

    private fun initData(storesDetailsList: ArrayList<StoresDetail>) {
    setRecyclerViewAdapter(storesDetailsList)
    }

    private fun setRecyclerViewAdapter(storesDetailsList: ArrayList<StoresDetail>) {
        val adapter = object  : AppointmentTypeAdapter(context,storesDetailsList){
            override fun onClickItem(storesDetail: StoresDetail) {
                startActivity(EmployeeActivity.getIntent(context!!,storesDetail))
            }

        }
        binding.recyclerView.adapter = adapter
    }

}