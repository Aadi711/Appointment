package com.example.appointment.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.appointment.adapters.EmployeeAdapter
import com.example.appointment.databinding.ActivityEmployeeBinding
import com.example.appointment.model.Employee
import com.example.appointment.model.ResponseModel
import com.example.appointment.model.Stores
import com.example.appointment.model.StoresDetail
import com.example.appointment.utils.Constants
import com.example.example.appointment.activities.BaseActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import com.example.appointment.utils.Common


class EmployeeActivity : BaseActivity() {
    val context = this@EmployeeActivity
    lateinit var binding: ActivityEmployeeBinding
    lateinit var employeeList: ArrayList<Employee>
    lateinit var storesDetail: StoresDetail
    var scrollRange = -1
    var isShow = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        binding.toolbarText.text = "Details"
        binding.backArrowLayout.setOnClickListener {
            onBackPressed()
        }
        binding.appbarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange;
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                    binding.customToolbar.visibility = View.GONE
                } else if (isShow) {
                    isShow = false
                    binding.customToolbar.visibility = View.VISIBLE
                }

            })
        storesDetail = intent.getSerializableExtra(Constants.STORES) as StoresDetail
        initRecyclerView(binding.recyclerView, context)
        getEmployeeDetails()

        binding.swipeRefreshLayout.setOnRefreshListener {
            getEmployeeDetails()

        }
    }

    private fun getEmployeeDetails() {
          binding.swipeRefreshLayout.isRefreshing = true
        val call =
            getApiInterface().storesWithEmployees(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
                storesDetail.id.toString()
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

                val data = getResponseData(response.body(), context,binding.errorLayout)
                if (data != null) {
                    try {
                        val gson = Gson()
                        val json = gson.toJson(data)

                        val stores =
                            gson.fromJson(json, Stores::class.java)
                        if (stores.storesDetailsWithEmployees != null) {
                            setShopDetails(stores.storesDetailsWithEmployees)
                            initData(stores.storesDetailsWithEmployees.employeeList)
                        }
                    } catch (e: Exception) {
                                   binding.swipeRefreshLayout.isRefreshing = false

                        e.printStackTrace()
                    }
                }
            }
        })
    }

    private fun setShopDetails(storesDetailsWithEmployees: StoresDetail) {
        Glide.with(context).load(Constants.BASE_URL_IMAGES + storesDetailsWithEmployees.image).into(binding.image)
        binding.storeName.text = storesDetailsWithEmployees.name
        binding.storeAddress.text = storesDetailsWithEmployees.address
        binding.storeTime.text = Common.dateFormat.print(Common.serverDateFormat.parseDateTime(storesDetailsWithEmployees.createdAt))
    }

    private fun initData(employeeList: ArrayList<Employee>) {
        setRecyclerViewAdapter(employeeList)
    }


    private fun setRecyclerViewAdapter(employeeList: ArrayList<Employee>) {
        val adapter = object : EmployeeAdapter(context, employeeList) {
            override fun onClickItem(employee: Employee) {
                employee.shopName = binding.storeName.text.toString()
                startActivity(EmployeeProfileActivity.getIntent(context!!,employee))
            }

        }
        binding.recyclerView.adapter = adapter
    }

    companion object {
        fun getIntent(context: Context, storesDetail: StoresDetail): Intent {
            return Intent(context, EmployeeActivity::class.java)
                .putExtra(Constants.STORES, storesDetail)
        }
    }

}