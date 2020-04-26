package com.seminario2.mecanicaapp

import android.app.Application
import com.facebook.stetho.Stetho


class SigaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

}