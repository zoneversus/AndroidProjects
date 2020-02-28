package com.example.workermanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       /*Unique petition for a worker schedule task*/
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val compressionWork = OneTimeWorkRequestBuilder<ExecWorker>()
            .setConstraints(constraints)
            .build()
        /*Periodic petition for a worker schedule task*/

        val work = PeriodicWorkRequestBuilder<ExecWorker>( 20, TimeUnit.MINUTES).addTag("work")
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork("work",ExistingPeriodicWorkPolicy.KEEP,work)


    }
}
