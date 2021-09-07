package com.example.appointment.utils

import android.app.ProgressDialog
import android.content.Context

class ProgressUtils(context: Context) {

    private var progressDialog: ProgressDialog? = null

    init {
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage("Processing\nPlease Wait...")
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setCancelable(false)
    }


    fun showProgress() {
        try {
            progressDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showProgress(message : String) {
        try {
            progressDialog!!.setMessage(message)
            progressDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgress() {
        try {
            progressDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}