package com.example.camera

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco


class CameraApplication : Application() {

    companion object {
        lateinit var instance: com.example.camera.CameraApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(this)
    }
}
