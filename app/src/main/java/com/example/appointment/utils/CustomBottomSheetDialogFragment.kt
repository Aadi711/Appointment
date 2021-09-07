package com.example.appointment.utils

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appointment.databinding.PhotoSelectionBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CustomBottomSheetDialogFragment  : BottomSheetDialogFragment() {

    private var itemClickListener : ItemClickListener?=null
    lateinit var binding: PhotoSelectionBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PhotoSelectionBottomSheetBinding.inflate(LayoutInflater.from(activity),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        binding.galleryView.setOnClickListener {
        itemClickListener!!.onItemClick(binding.gallery.text.toString())
        }
        binding.cameraView.setOnClickListener {
            itemClickListener!!.onItemClick(binding.camera.text.toString())

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
