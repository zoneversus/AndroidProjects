package com.example.application2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "report_entity")
data class ReportEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,
    var name:String = "",
    var date:String = "00/00/0000",
    var isDone:Boolean = false
)
