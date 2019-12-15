package com.example.application2.actividades

import android.app.Application
import androidx.room.Room
import com.example.application2.database.MusicDatabase

class MusicApp: Application() {

    companion object {
        lateinit var database: MusicDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, MusicDatabase::class.java, "music-db").build()
    }

}
