package com.example.appointment.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appointment.R
import com.example.appointment.databinding.TimeLayoutBinding
import com.example.appointment.model.Appointment
import com.example.appointment.utils.Constants

abstract class TimeAdapter(val context: Context?, private val list: ArrayList<Appointment>) :
    RecyclerView.Adapter<TimeAdapter.AppointmentHolder>() {
    private var checkedPosition = -1

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AppointmentHolder {
        val binding =
            TimeLayoutBinding.inflate(LayoutInflater.from(context), p0, false)
        return AppointmentHolder(binding)

    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun getCompleteList() : List<Appointment> {
        return list
    }

    fun getSelected() : Appointment? {
        if (checkedPosition != -1) {
            return list[checkedPosition]
        }
        return null
    }

    override fun onBindViewHolder(holder: AppointmentHolder, p1: Int) {
        val appointment = list[p1]
        val status = appointment.status
        if (status == Constants.BOOKED) {
            holder.binding.mainLayout.backgroundTintList = ActivityCompat.getColorStateList(
                context!!,
                R.color.tabs_item_background_default_color
            )
            holder.binding.time.setTextColor(
                ActivityCompat.getColor(
                    context,
                    R.color.tabs_item_default_color
                )
            )
        }

        holder.binding.time.text = appointment.time
        holder.itemView.setOnClickListener {
            if (status != Constants.BOOKED) {
                checkedPosition = holder.adapterPosition
                notifyDataSetChanged()
                onClickItem(appointment)
            }

        }

        if (checkedPosition == -1) {
            if (status != Constants.BOOKED) {

                holder.binding.mainLayout.backgroundTintList =
                    ActivityCompat.getColorStateList(context!!, R.color.text_blue_color)
                holder.binding.time.setTextColor(ActivityCompat.getColor(context, R.color.white))

            }

        } else {
            if (checkedPosition == holder.adapterPosition) {
                if (status != Constants.BOOKED) {

                    holder.binding.mainLayout.backgroundTintList =
                        ActivityCompat.getColorStateList(context!!, R.color.confirmed_color)
                    holder.binding.time.setTextColor(
                        ActivityCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                }
            } else {
                if (status != Constants.BOOKED) {

                    holder.binding.mainLayout.backgroundTintList =
                        ActivityCompat.getColorStateList(context!!, R.color.text_blue_color)
                    holder.binding.time.setTextColor(
                        ActivityCompat.getColor(
                            context,
                            R.color.white
                        )
                    )
                }
            }
        }
    }


    class AppointmentHolder(val binding: TimeLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    abstract fun onClickItem(appointment: Appointment)
}

