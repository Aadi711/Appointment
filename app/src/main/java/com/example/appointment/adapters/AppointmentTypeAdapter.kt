package com.example.appointment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appointment.databinding.MainAppointmentRecyclerLayoutBinding
import com.example.appointment.model.AppointmentType
import com.example.appointment.model.StoresDetail
import com.example.appointment.utils.Common
import com.example.appointment.utils.Constants

abstract class AppointmentTypeAdapter(val context: Context?, val list: ArrayList<StoresDetail>) :
    RecyclerView.Adapter<AppointmentTypeAdapter.AppointmentHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AppointmentHolder {
        val binding =
            MainAppointmentRecyclerLayoutBinding.inflate(LayoutInflater.from(context), p0, false)
        return AppointmentHolder(binding)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AppointmentHolder, p1: Int) {
        val storesDetail = list[p1]
        holder.binding.appointmentName.text = storesDetail.name
        holder.binding.appointmentTime.text = Common.dateFormat.print(Common.serverDateFormat.parseDateTime(storesDetail.createdAt))
        holder.binding.appointmentAddress.text = storesDetail.address
        Glide.with(context!!).load(Constants.BASE_URL_IMAGES + storesDetail.image).into(holder.binding.appointmentImage)

        holder.itemView.setOnClickListener {

            onClickItem(storesDetail)
        }
    }


class AppointmentHolder(val binding: MainAppointmentRecyclerLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
}

abstract fun onClickItem(storesDetail: StoresDetail)
}

