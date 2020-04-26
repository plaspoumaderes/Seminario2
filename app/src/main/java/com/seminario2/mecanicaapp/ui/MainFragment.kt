package com.seminario2.mecanicaapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.model.Car
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auto = Car("Fiat", "Uno", 2009)
        fr_main_car.setCar(auto)
        fr_main_status.setStatus(1)
    }

}