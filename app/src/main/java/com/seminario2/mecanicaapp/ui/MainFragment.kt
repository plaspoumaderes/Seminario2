package com.seminario2.mecanicaapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.model.Car
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        const val CAR_KEY = "car-key-arguments"
        fun newInstance(car: Car): MainFragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putString(CAR_KEY, Gson().toJson(car))
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(CAR_KEY)?.let { carString ->
            val auto = Gson().fromJson(carString, Car::class.java)
            fr_main_car.setCar(auto)
            fr_main_status.setStatus(auto.status)
        }
    }

}