package com.example.myapplication.activities

import android.app.Application
import androidx.room.Room
import com.example.myapplication.database.TaskDatabase

class MisNotasApp: Application() {

    companion object {
        lateinit var database: TaskDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, TaskDatabase::class.java, "tasks-db").build()
    }

}
