package com.example.application2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.application2.dao.ReportDao
import com.example.application2.entity.ReportEntity

@Database(entities = arrayOf(ReportEntity::class), version = 1)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun reportDao(): ReportDao
}
