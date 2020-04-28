package com.seminario2.mecanicaapp.view

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.commons.extension.setImageCar
import com.seminario2.mecanicaapp.model.Car
import kotlinx.android.synthetic.main.view_car.view.*

class CarView : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : this(
        context,
        attrs,
        defStyle,
        0
    )

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int, defStyleRes: Int) : super(
        context, attrs
    ) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        View.inflate(context, R.layout.view_car, this)
    }

    fun setCar(car: Car) {
        v_car_marca_value.text = car.brand
        v_car_modelo_value.text = car.modelo
        v_car_año_value.text = car.year.toString()
        v_car_marca_img.setImageCar(car.brand)
    }

}