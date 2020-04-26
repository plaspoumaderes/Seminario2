package com.seminario2.mecanicaapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seminario2.mecanicaapp.commons.constants.Constants
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.ui.LoginFragment
import com.seminario2.mecanicaapp.ui.MainFragment


class MainActivity : AppCompatActivity() {

    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSharedPreferences(Constants.SIGA_PREFS, Context.MODE_PRIVATE)?.let {
            prefs = it
            if (it.contains(Constants.USER_ID)) {
                replaceFragment(MainFragment.newInstance(), false)
            } else {
                replaceFragment(LoginFragment.newInstance(), false)
            }
        }
        //replaceFragment(MainFragment.newInstance(), false)

    }

}
