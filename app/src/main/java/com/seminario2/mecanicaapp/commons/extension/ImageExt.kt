package com.seminario2.mecanicaapp.commons.extension

import android.content.res.Resources
import android.util.Log
import android.widget.ImageView

fun ImageView.setImageCar(brand: String) {
    val resources: Resources = context.resources
    val resourceId: Int = resources.getIdentifier(
        brand.toLowerCase(), "drawable",
        context.packageName
    )
    try {
        this.setImageDrawable(resources.getDrawable(resourceId))
    } catch (e: Resources.NotFoundException) {
        Log.i("Test", "NotFoundException -> $e")
    }
}