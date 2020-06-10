package com.seminario2.mecanicaapp.view

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieDrawable
import com.seminario2.mecanicaapp.R
import kotlinx.android.synthetic.main.view_status_car.view.*

class StatusCarView : ConstraintLayout {

    private var alphaAnimation: AlphaAnimation? = null

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
        View.inflate(context, R.layout.view_status_car, this)
    }

    fun setStatus(status: Int) {
        alphaAnimation?.let {
            it.cancel()
        }
        alphaAnimation = AlphaAnimation(0.0f, 1.0f).apply {
            duration = 1000
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
        when (status) {
            1 -> {
                v_status_pending.startAnimation(alphaAnimation)
                v_status_lottie.setAnimation("lottie-car-scan.json")
            }
            2 -> {
                v_status_scanner.startAnimation(alphaAnimation)
                v_status_lottie.setAnimation("lottie-car-scan.json")
                v_status_lottie.repeatCount = ValueAnimator.INFINITE
            }
            3 -> {
                v_status_working.startAnimation(alphaAnimation)
                v_status_lottie.setAnimation("lottie-reparacion.json")
                v_status_lottie.repeatCount = ValueAnimator.INFINITE
            }
            4 -> {
                v_status_done.startAnimation(alphaAnimation)
                v_status_lottie.setAnimation("lottie-ok.json")
                v_status_lottie.setPadding(16,16,16,16)
            }
        }
        if (status > 0) v_status_lottie.playAnimation()
    }
}