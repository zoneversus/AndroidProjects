package com.example.application2.actividades

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class CamaraApp : Application() {

    companion object {
        lateinit var instance: com.example.application2.actividades.CamaraApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(this)
    }
}
