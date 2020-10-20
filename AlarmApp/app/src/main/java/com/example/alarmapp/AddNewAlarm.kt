package com.example.alarmapp

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import com.example.busfleets.trainSchedule.Alarm
import com.example.busfleets.trainSchedule.ApplicationDatabase
import kotlinx.android.synthetic.main.activity_add_new_alarm.*
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

class AddNewAlarm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_alarm)
        addAlarm()
    }

    @SuppressLint("SimpleDateFormat")
    private fun addAlarm(){
        val cal = Calendar.getInstance()
        alarm_time_picker.setOnTimeChangedListener { view, h, m ->
            cal.set(Calendar.HOUR_OF_DAY, h)
            cal.set(Calendar.MINUTE, m)

        }

        alarm_add_btn.setOnClickListener {
            AsyncTask.execute{
                ApplicationDatabase.getInstance(applicationContext)!!.getAlarmDao().insertAlarm(
                    Alarm(status = true, description =  alarm_description.text.toString(), hour = cal.get(Calendar.HOUR_OF_DAY), minute = cal.get(Calendar.MINUTE))
                )
                runOnUiThread {
                    finish()
                }
            }
        }
    }
}