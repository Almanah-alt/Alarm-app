package com.example.busfleets.trainSchedule

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Alarm::class], version = 1)
abstract class ApplicationDatabase: RoomDatabase(){

    abstract fun getAlarmDao(): AlarmDao

    companion object{
        private const val DB_NAME = "alarmApp.db"
        private var instance: ApplicationDatabase? = null
        fun getInstance(context: Context): ApplicationDatabase? {
            if(instance == null){
                instance = Room.databaseBuilder(context,
                    ApplicationDatabase::class.java, DB_NAME).build()
            }
            return instance
        }
    }

}