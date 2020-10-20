package com.example.alarmapp

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.busfleets.trainSchedule.Alarm
import kotlinx.android.synthetic.main.alarm_item.view.*

class MyAdapter(
    private val itemList: List<Alarm> = listOf(),
    private val onItemLongClick:(Alarm) -> Unit,
    private val onItemShortClick:(Alarm) -> Unit
) :RecyclerView.Adapter<MyAdapter.HintViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alarm_item, parent, false)
        return HintViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
        holder.bindHint(itemList[position])
    }


    inner class HintViewHolder(
        private val view: View
    ):RecyclerView.ViewHolder(view){
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bindHint(alarm: Alarm){

            view.alarm_time.text = "${alarm.hour} : ${alarm.minute}"
            view.alarm_description.text = alarm.description

            view.alarm_switch.isChecked = alarm.status

            view.setOnLongClickListener {
                onItemLongClick(alarm)
                return@setOnLongClickListener true
            }

            view.setOnClickListener {
                onItemShortClick(alarm)
            }
        }
    }
}
