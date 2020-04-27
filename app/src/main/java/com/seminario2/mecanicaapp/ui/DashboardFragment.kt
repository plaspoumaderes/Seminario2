package com.seminario2.mecanicaapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.model.Car
import kotlinx.android.synthetic.main.fragment_main.*

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}