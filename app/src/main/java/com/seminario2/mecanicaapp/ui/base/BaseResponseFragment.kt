package com.seminario2.mecanicaapp.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.commons.extension.gone
import kotlinx.android.synthetic.main.fragment_base_response.*

abstract class BaseResponseFragment : BaseFragment(R.layout.fragment_base_response) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    abstract fun getStatus(): Status

    abstract fun getTitle(): String
    abstract fun getDescripcion(): String

    abstract fun getPrimaryButtonText(): String?
    abstract fun getPrimaryButtonListener(): View.OnClickListener?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fr_base_res_title.text = getTitle()
        fr_base_res_desc.text = getDescripcion()
        configurateButtons()
        addListener()
        playAnimation()
    }

    private fun configurateButtons() {
        getPrimaryButtonText()?.let {
            fr_base_res_primary_btn.text = it
        } ?: run {
            fr_base_res_primary_btn.gone()
        }
    }

    private fun playAnimation() {
        when (getStatus()) {
            Status.SUCCESS -> fr_base_res_img.setAnimation(getString(R.string.lottie_success))
            Status.ERROR -> fr_base_res_img.setAnimation(getString(R.string.lottie_error))
        }
        fr_base_res_img.playAnimation()
    }

    private fun addListener() {
        fr_base_res_primary_btn.setOnClickListener(getPrimaryButtonListener())
    }
}
