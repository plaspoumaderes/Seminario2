package com.seminario2.mecanicaapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.seminario2.mecanicaapp.commons.constants.Constants
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.commons.listener.OnBackPressedListener
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
                    (application as SigaApplication).loginResponse =
                        Gson().fromJson(this, LoginResponse::class.java)
                }
                replaceFragment(DashboardFragment.newInstance(), false)
            } else {
                replaceFragment(LoginFragment.newInstance(), false)
            }
        }
    }

    fun logUser(body: LoginResponse) {
        getSharedPreferences(Constants.SIGA_PREFS, Context.MODE_PRIVATE)?.let {
            it.edit().apply {
                putString(Constants.USER_ID, Gson().toJson(body))
                apply()
            }
        }
        (application as SigaApplication).loginResponse = body
        replaceFragment(DashboardFragment.newInstance(), false)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.fragments.lastOrNull()
        fragment?.let {
            if (it is OnBackPressedListener) {
                if (!(it as OnBackPressedListener).onBackPressed()) {
                    super.onBackPressed()
                }
            } else {
                super.onBackPressed()
            }
        } ?: run {
            super.onBackPressed()
        }
    }

}
