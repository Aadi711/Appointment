package com.example.appointment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.appointment.R
import com.example.appointment.activities.HelpActivity
import com.example.appointment.activities.PersonalDetailsActivity
import com.example.appointment.activities.PrivacyPolicyActivity
import com.example.appointment.activities.SignInActivity
import com.example.appointment.databinding.FragmentProfileBinding
import com.example.appointment.model.Profile
import com.example.appointment.model.ResponseModel
import com.example.appointment.model.Stores
import com.example.appointment.model.User
import com.example.appointment.utils.Common
import com.example.appointment.utils.Constants
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : BaseFragment() {

    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    private fun initViews() {
        binding.profileLayout.setOnClickListener {
            startActivity(PersonalDetailsActivity.getIntent(context))
        }
        binding.privacyPolicyLayout.setOnClickListener {
            startActivity(PrivacyPolicyActivity.getIntent(context))
        }
        binding.helpLayout.setOnClickListener {
            startActivity(HelpActivity.getIntent(context))
        }
        binding.layoutLogout.setOnClickListener {
            Common.logOut(context)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            getUserProfile()
        }
        getUserProfile()
    }

    private fun getUserProfile() {
        binding.swipeRefreshLayout.isRefreshing = true
        val call =
            getApiInterface().userProfile(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
                Common.getUserFromSharedPreferences(context).id.toString()
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

                        val user =
                            gson.fromJson(json, User::class.java)
                        if (user != null) {
                            setUserProfile(user)

                        }
                    } catch (e: Exception) {
                        binding.swipeRefreshLayout.isRefreshing = false

                        e.printStackTrace()
                    }
                }
            }
        })
    }

    private fun setUserProfile(user: User) {

        Glide.with(requireContext()).load(user.image).placeholder(R.drawable.appointment_logo).into(binding.userImage)
        binding.userEmail.text = user.email
        binding.userPhone.text = user.phoneNumber
        binding.userName.text = user.name
    }


}