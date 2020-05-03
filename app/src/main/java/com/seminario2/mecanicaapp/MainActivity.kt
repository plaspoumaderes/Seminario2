package com.seminario2.mecanicaapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seminario2.mecanicaapp.commons.constants.Constants
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.model.LoginResponse
import com.seminario2.mecanicaapp.ui.DashboardFragment
import com.seminario2.mecanicaapp.ui.login.LoginFragment


class MainActivity : AppCompatActivity() {

    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSharedPreferences(Constants.SIGA_PREFS, Context.MODE_PRIVATE)?.let {
            prefs = it
            if (it.contains(Constants.USER_ID)) {
                it.getString(Constants.USER_ID, null)?.apply {
                    (application as SigaApplication).loginResponse = LoginResponse(this)
                }
                replaceFragment(DashboardFragment.newInstance(), false)
            } else {
                replaceFragment(LoginFragment.newInstance(), false)
            }
        }
        //TODO falla servicio logina
//        (application as SigaApplication).loginResponse = LoginResponse("a")
//        replaceFragment(DashboardFragment.newInstance(), false)
    }

}
