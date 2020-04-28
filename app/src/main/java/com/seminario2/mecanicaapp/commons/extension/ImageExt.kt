package com.seminario2.mecanicaapp.commons.extension

import android.content.res.Resources
import android.widget.ImageView

fun ImageView.setImageCar(brand: String) {
    val resources: Resources = context.resources
    val resourceId: Int = resources.getIdentifier(
        brand.toLowerCase(), "drawable",
        context.packageName
    )
    this.setImageDrawable(resources.getDrawable(resourceId))
}