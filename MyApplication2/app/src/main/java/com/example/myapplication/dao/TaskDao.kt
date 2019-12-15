package com.example.myapplication.dao

import androidx.room.*
import com.example.myapplication.entity.TaskEntity
@Dao
interface TaskDao {
    @Query("SELECT * FROM task_entity")
    fun getAllTasks(): MutableList<TaskEntity>

    @Insert
    fun addTask(taskEntity : TaskEntity):Long

    @Query("SELECT * FROM task_entity where id like :id")
    fun getTaskById(id: Long): TaskEntity

    @Delete
    fun deleteTask(taskEntity: TaskEntity):Int

    @Update
    fun updateTask(taskEntity: TaskEntity):Int

}