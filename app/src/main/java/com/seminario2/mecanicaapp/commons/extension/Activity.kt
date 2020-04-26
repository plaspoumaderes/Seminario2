package com.seminario2.mecanicaapp.commons.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.seminario2.mecanicaapp.R

fun AppCompatActivity.replaceFragment(fragment: Fragment, addToBack: Boolean = true, withAnimation: Boolean = true) {
    supportFragmentManager.inTransaction {
        if (withAnimation) setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
        replace(R.id.mecanicapp_main_fragment, fragment)
        if (addToBack)
            addToBackStack(fragment.tag)
    }
}

fun AppCompatActivity.addFragment(fragment: Fragment, addToBack: Boolean = true, withAnimation: Boolean = true) {
    supportFragmentManager.inTransaction {
        if (withAnimation) setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
        add(R.id.mecanicapp_main_fragment, fragment)
        if (addToBack)
            addToBackStack(fragment.tag)
    }
}

