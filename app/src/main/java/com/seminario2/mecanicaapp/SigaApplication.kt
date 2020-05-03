package com.seminario2.mecanicaapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.seminario2.mecanicaapp.model.LoginResponse


class SigaApplication : Application() {

    var loginResponse: LoginResponse? = null

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

}