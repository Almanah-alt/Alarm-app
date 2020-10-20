package com.example.alarmapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busfleets.trainSchedule.Alarm
import com.example.busfleets.trainSchedule.ApplicationDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_alert_dialog.view.*
import kotlinx.android.synthetic.main.custom_delete_dialog.*
import kotlinx.android.synthetic.main.custom_delete_dialog.view.*
import java.sql.Time
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addAlarm()
        displayAlarm()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        displayAlarm()
        super.onResume()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayAlarm(){
        alarm_list.layoutManager = LinearLayoutManager(this)

        AsyncTask.execute {
            val alarms = ApplicationDatabase.getInstance(applicationContext)
                ?.getAlarmDao()?.getAlarm()

            runOnUiThread{
                alarm_list.adapter = alarms?.let {
                    MyAdapter(itemList = it, onItemLongClick = { item ->
                        val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_delete_dialog, null)
                        val dialogBuilder = AlertDialog.Builder(this)
                            .setView(dialogView)
                        val  alertDialog = dialogBuilder.show()
                        dialogBuilder.setMessage("Do you want to delete?")
                        dialogView.no_txt.setOnClickListener {
                            alertDialog.dismiss()
                        }
                        dialogView.yes_txt.setOnClickListener {
                            deleteItem(item)
                            val intent = intent
                            finish()
                            startActivity(intent)
                        }
                    }, onItemShortClick = {
                        val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog, null)
                        val dialogBuilder = AlertDialog.Builder(this)
                            .setView(dialogView)
                        val  alertDialog = dialogBuilder.show()
                        val currentTime = LocalTime.now()
                        val dateHour: Int = it.hour
                        var differenceHour = dateHour.minus(currentTime.hour)
                        if (differenceHour <= 0){
                            differenceHour += 24
                            dialogView.hour_notification.text = "$differenceHour hours left"
                        }
                        dialogView.hour_notification.text = "$differenceHour hours left"



                        dialogView.ok_btn.setOnClickListener {
                            alertDialog.dismiss()
                        }

                    })
                }
            }
        }
        designRecyclerView()
    }
    private fun designRecyclerView(){
        (alarm_list.layoutManager as LinearLayoutManager).reverseLayout = true
        (alarm_list.layoutManager as LinearLayoutManager).stackFromEnd = true
        val mDividerItemDecoration = DividerItemDecoration(
            alarm_list.context,
            (alarm_list.layoutManager as LinearLayoutManager).orientation
        )
        alarm_list.addItemDecoration(mDividerItemDecoration)
    }

    private fun addAlarm(){
        new_alarm_btn.setOnClickListener {
            val intent = Intent(this, AddNewAlarm::class.java)
            startActivity(intent)
        }
    }

    private fun deleteItem(alarm: Alarm) {
        AsyncTask.execute {
            ApplicationDatabase.getInstance(applicationContext)!!
                .getAlarmDao()
                .deleteItem(alarm)
        }
    }

}