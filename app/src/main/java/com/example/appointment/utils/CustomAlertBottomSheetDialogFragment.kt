package com.example.appointment.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appointment.databinding.AlertDialogBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CustomAlertBottomSheetDialogFragment  : BottomSheetDialogFragment() {

    private var itemClickListener : ItemClickListener?=null
    lateinit var binding: AlertDialogBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlertDialogBottomSheetBinding.inflate(LayoutInflater.from(activity),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.yesButton.setOnClickListener {
        itemClickListener!!.onItemClick(Constants.YES)
        }
        binding.noButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemClickListener = if(context is ItemClickListener){
            context
        }else{
            throw RuntimeException(context.toString() + "Must Implement Listener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        itemClickListener = null
    }

}
