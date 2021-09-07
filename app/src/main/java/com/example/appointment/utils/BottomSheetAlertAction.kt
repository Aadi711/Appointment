package com.example.appointment.utils

class BottomSheetAlertAction {
    companion object{
        const val TAG = "BottomSheetAction"
        fun newInstance() : CustomAlertBottomSheetDialogFragment{
            return CustomAlertBottomSheetDialogFragment()
        }
    }
}