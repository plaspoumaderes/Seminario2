package com.seminario2.mecanicaapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.model.FixModelResponse
import com.seminario2.mecanicaapp.model.Vehicle
import com.seminario2.mecanicaapp.ui.chat.ChatFragment
import com.seminario2.mecanicaapp.ui.garage.EvaluateFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private lateinit var fix: FixModelResponse
    private var isAccept: Boolean = false

    companion object {
        const val CAR_KEY = "car-key-arguments"
        fun newInstance(vehicle: FixModelResponse): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putString(CAR_KEY, Gson().toJson(vehicle))
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(CAR_KEY)?.let { carString ->
            fix = Gson().fromJson(carString, FixModelResponse::class.java)
            fix.vehicle?.let {
                fr_main_car.setCar(it)
            }
            fr_main_status.setStatus(fix.fixStatusNumber)
            setData(fix)
        }
        addListener()
    }

    private fun addListener() {
        fr_main_money_img.setOnClickListener {
            isAccept = !isAccept
            setImageCheck()
            //Execute Service
        }
        fr_main_chat.setOnClickListener {
            (activity as AppCompatActivity).replaceFragment(ChatFragment.newInstance(fix))
        }
    }

    private fun setData(fix: FixModelResponse?) {
        fix?.let { f ->
            f.fixIngress?.let { ingres ->
                fr_main_ingress_value.text = "${ingres.date}/${ingres.month}"
            }
            f.fixEgress?.let { egres ->
                fr_main_egress_value.text = "${egres.date}/${egres.month}"
            }

            isAccept = f.fixFinalPriceAccept
            if (f.fixFinalPrice > 0) {
                if (f.fixFinalPriceAccept) {
                    fr_main_money_img.isEnabled = false
                }
                fr_main_money_value.text = "$ ${f.fixFinalPrice}"
                setImageCheck()
            } else {
                fr_main_money_img.isEnabled = false
            }
        }
    }

    private fun setImageCheck() {
        fr_main_money_img.setImageDrawable(
            if (isAccept)
                resources.getDrawable(R.drawable.ic_check_ok)
            else resources.getDrawable(
                R.drawable.ic_check_
            )
        )
    }

}