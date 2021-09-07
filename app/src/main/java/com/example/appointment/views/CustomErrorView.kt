package com.example.appointment.views

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.appointment.databinding.CustomErrorLayoutBinding

abstract class CustomErrorView : View {

    var binding: CustomErrorLayoutBinding
    var message : String
    private val TAG = "CustomErrorView"
    constructor(context: Context,message : String) : super(context) {
        binding = CustomErrorLayoutBinding.inflate(LayoutInflater.from(context), null, false)
        this.message = message
        initViews()

    }

    private fun initViews() {
        binding.warningView.visibility = VISIBLE
        Log.d(TAG, "initViews: MESSAGE $message")
        binding.warningText.text = message
    }

    fun getView(): View {
        return binding.root
    }
}