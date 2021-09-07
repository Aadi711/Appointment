package com.example.appointment.utils

class BottomSheetAction {
    companion object{
        const val TAG = "BottomSheetAction"
        fun newInstance() : CustomBottomSheetDialogFragment{
            return CustomBottomSheetDialogFragment()
        }
    }
}