package com.example.appointment.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appointment.R
import com.example.appointment.databinding.AppointmentRecyclerLayoutBinding
import com.example.appointment.databinding.EmployeesRecyclerLayoutBinding
import com.example.appointment.model.Appointment
import com.example.appointment.model.Employee
import com.example.appointment.utils.Constants

abstract class EmployeeAdapter(val context: Context?, private val list: ArrayList<Employee>) :
    RecyclerView.Adapter<EmployeeAdapter.AppointmentHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AppointmentHolder {
        val binding =
            EmployeesRecyclerLayoutBinding.inflate(LayoutInflater.from(context), p0, false)
        return AppointmentHolder(binding)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AppointmentHolder, p1: Int) {
        val employee = list[p1]
        Glide.with(context!!).load(employee.image).into(holder.binding.userImage)
        holder.binding.employeeName.text = employee.name
        holder.binding.employeePhone.text = employee.phone

        holder.itemView.setOnClickListener {

            onClickItem(employee)
        }
    }


    class AppointmentHolder(val binding: EmployeesRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    abstract fun onClickItem(employee: Employee)
}

