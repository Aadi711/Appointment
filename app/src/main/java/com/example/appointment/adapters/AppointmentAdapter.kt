package com.example.appointment.adapters

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appointment.R
import com.example.appointment.databinding.AppointmentRecyclerLayoutBinding
import com.example.appointment.model.Appointment
import com.example.appointment.model.Result
import com.example.appointment.utils.Common
import com.example.appointment.utils.Constants

abstract class AppointmentAdapter(val context: Context?, private val list: ArrayList<Result>) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AppointmentHolder {
        val binding =
            AppointmentRecyclerLayoutBinding.inflate(LayoutInflater.from(context), p0, false)
        return AppointmentHolder(binding)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AppointmentHolder, p1: Int) {
        val result = list[p1]
        val status = result.status
        when {
            TextUtils.equals(status, "3") -> {
                holder.binding.imageLayout.background =
                    ActivityCompat.getDrawable(context!!, R.drawable.layout_cancelled_end_line)
                holder.binding.appointmentStatus.setTextColor(ActivityCompat.getColor(context,R.color.cancelled_color))
                holder.binding.appointmentStatus.text = "Cancelled"

                holder.binding.appointmentDescription.text = "Your appointment with " + Html.fromHtml("<b>" + result.name + "</b>") + " due to some reason"

            }
            TextUtils.equals(status, "2") -> {
                holder.binding.imageLayout.background =
                    ActivityCompat.getDrawable(context!!, R.drawable.layout_confirmed_end_line)

                holder.binding.appointmentStatus.setTextColor(ActivityCompat.getColor(context,R.color.confirmed_color))
                holder.binding.appointmentStatus.text = "Confirmed"

                holder.binding.appointmentDescription.text = "Your appointment with " + Html.fromHtml("<b>" + result.name + "</b>") + " is confirmed "
            }

            TextUtils.equals(status, "1") -> {
                holder.binding.imageLayout.background =
                    ActivityCompat.getDrawable(context!!, R.drawable.layout_pending_end_line)

                holder.binding.appointmentStatus.setTextColor(ActivityCompat.getColor(context,R.color.pending_color))
                holder.binding.appointmentStatus.text = "Pending"

                holder.binding.appointmentDescription.text = "Your appointment with " + Html.fromHtml("<b>" + result.name + "</b>") + " is pending "

            }
        }
        Glide.with(context!!).load(result.image).into(holder.binding.userImage)

        holder.binding.userName.text = result.name
        holder.binding.appointmentTimingAndAddress.text = Common.dateFormat.print(Common.serverDateFormat.parseDateTime(result.createdAt)) +" "+ result.time

        Glide.with(context).load(result.image).into(holder.binding.userImage)

        holder.itemView.setOnClickListener {

            onClickItem(result)
        }
    }


    class AppointmentHolder(val binding: AppointmentRecyclerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    abstract fun onClickItem(result: Result)
}

