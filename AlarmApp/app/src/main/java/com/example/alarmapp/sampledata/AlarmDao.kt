package com.example.busfleets.trainSchedule


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.selects.select

@Dao
interface AlarmDao {

    @Insert
    fun insertAlarm(alarm: Alarm)

    @Delete
    fun deleteItem(alarm: Alarm)

    @Query("Select * FROM alarms")
    fun getAlarm(): List<Alarm>



}