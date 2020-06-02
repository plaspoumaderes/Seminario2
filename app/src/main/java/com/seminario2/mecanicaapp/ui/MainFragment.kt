package com.seminario2.mecanicaapp.ui

import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.model.FixModelResponse
import com.seminario2.mecanicaapp.model.Vehicle
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(R.layout.fragment_main) {

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
            val fix = Gson().fromJson(carString, FixModelResponse::class.java)
            fix.vehicle?.let {
                fr_main_car.setCar(it)
            }
            fr_main_status.setStatus(fix.fixStatusNumber)
        }
    }

}