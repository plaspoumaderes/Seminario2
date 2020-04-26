package com.seminario2.mecanicaapp.commons.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commitAllowingStateLoss()
}

fun Fragment.setupToolbar(toolbar: Toolbar) {
        activity?.let { act ->
                val appCompatActivity = (act as AppCompatActivity)
                with(appCompatActivity) {
                        setSupportActionBar(toolbar)
                        supportActionBar?.let { actBar ->
                                with(actBar) {
                                        setDisplayHomeAsUpEnabled(true)
                                        setDisplayShowHomeEnabled(true)
                                }
                        }
                }
                toolbar.setNavigationOnClickListener { appCompatActivity.onBackPressed() }
                setHasOptionsMenu(true)
        }
}