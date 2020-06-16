package com.seminario2.mecanicaapp.commons.extension

import android.view.View

fun View.visible() { this.visibility = View.VISIBLE }

fun View.gone() { this.visibility = View.GONE }

fun View.invisible() { this.visibility = View.INVISIBLE }

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()