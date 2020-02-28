package com.example.workermanager

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class ExecWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        // Do the work here
        Log.d("Worker desu","Condiciones cumplidas")
        // Indicate whether the task finished successfully with the Result
        return Result.success()
    }
}