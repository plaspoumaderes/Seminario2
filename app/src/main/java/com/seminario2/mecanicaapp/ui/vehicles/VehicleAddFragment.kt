package com.seminario2.mecanicaapp.ui.vehicles

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.model.Vehicle
import kotlinx.android.synthetic.main.fragment_vehicle_add.*
import retrofit2.Response

class VehicleAddFragment : BaseFragment(R.layout.fragment_vehicle_add) {

    companion object {
        fun newInstance(): VehicleAddFragment {
            return VehicleAddFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListener()
        addObservableView()
    }

    private fun addObservableView() {
        viewModel.addVehicleResponseMutable.observe(
            viewLifecycleOwner,
            Observer<Response<Vehicle>> { response ->
                viewModel.getVehiclesbyUserName(loginResponse)
                activity?.onBackPressed()
            })
    }

    private fun addListener() {
        fr_v_add_btn.setOnClickListener {
            viewModel.insertVehicle(
                Vehicle(
                    loginResponse.userName,
                    fr_v_add_plate.text.toString().trim(),
                    fr_v_add_brand.text.toString().trim(),
                    fr_v_add_modelo.text.toString().trim(),
                    fr_v_add_year.text.toString().toInt(),
                    fr_v_add_color.text.toString().trim(),
                    fr_v_add_type.text.toString().trim(),
                    fr_v_add_chasis.text.toString().trim(),
                    fr_v_add_engine.text.toString().trim(),
                    "", 0
                )
            )
        }
    }


}