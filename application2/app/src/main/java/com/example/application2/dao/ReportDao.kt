package com.example.application2.dao

import androidx.room.*
import com.example.application2.entity.ReportEntity

@Dao
interface ReportDao {
    @Query("SELECT * FROM report_entity")
    fun getAllReports(): MutableList<ReportEntity>

    @Query("SELECT * FROM report_entity ORDER BY date")
    fun getAllReportsOrderByDate(): MutableList<ReportEntity>

    @Insert
    fun addReport(reportEntity: ReportEntity):Long

    @Query("SELECT * FROM report_entity where id like :id")
    fun getReport(id: Long): ReportEntity

    @Delete
    fun deleteReport(reportEntity: ReportEntity):Int

    @Update
    fun updateReport(reportEntity: ReportEntity):Int

}