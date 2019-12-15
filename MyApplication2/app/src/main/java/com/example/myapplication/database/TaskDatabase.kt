package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.dao.TaskDao
import com.example.myapplication.entity.TaskEntity

@Database(entities = arrayOf(TaskEntity::class), version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
