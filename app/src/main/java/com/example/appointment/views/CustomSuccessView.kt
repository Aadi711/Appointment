package com.example.appointment.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.appointment.databinding.CustomSuccessLayoutBinding

abstract class CustomSuccessView : View {

    var binding: CustomSuccessLayoutBinding
    var message : String
    private val TAG = "CustomErrorView"
    constructor(context: Context,message : String) : super(context) {
        binding = CustomSuccessLayoutBinding.inflate(LayoutInflater.from(context), null, false)
        this.message = message
        initViews()

    }

    private fun initViews() {
        binding.successView.visibility = VISIBLE

        Log.d(TAG, "initViews: MESSAGE $message")
        binding.successText.text = message
    }

    fun getView(): View {
        return binding.root
    }
}