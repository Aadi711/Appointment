package com.example.appointment.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.appointment.databinding.CustomLoaderLayoutBinding

abstract class CustomLoaderView : View {

    var binding: CustomLoaderLayoutBinding

    private val TAG = "CustomErrorView"
    constructor(context: Context) : super(context) {
        binding = CustomLoaderLayoutBinding.inflate(LayoutInflater.from(context), null, false)
        initViews()

    }

    private fun initViews() {


    }

    fun getView(): View {
        return binding.root
    }
}