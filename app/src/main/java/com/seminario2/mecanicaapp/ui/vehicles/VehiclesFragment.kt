package com.seminario2.mecanicaapp.ui.vehicles

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.model.LoginResponse
import com.seminario2.mecanicaapp.model.Vehicle
import com.seminario2.mecanicaapp.ui.MainFragment
import com.seminario2.mecanicaapp.ui.vehicles.adapter.VehicleAdapter
import com.seminario2.mecanicaapp.viewmodel.SigaViewModel
import kotlinx.android.synthetic.main.fragment_vehicles.*
import retrofit2.Response

class VehiclesFragment : BaseFragment(R.layout.fragment_vehicles) {

    private lateinit var vehicleAdapter: VehicleAdapter

    companion object {
        fun newInstance(): VehiclesFragment {
            return VehiclesFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getVehiclesbyUserName(loginResponse)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAdapter()
        addListener()
        addObversable()
    }

    private fun loadAdapter() {
        vehicleAdapter = VehicleAdapter { car ->
            (activity as AppCompatActivity).replaceFragment(
                MainFragment.newInstance(
                    car
                )
            )
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fr_v_recycler.isNestedScrollingEnabled = false
        fr_v_recycler.layoutManager = layoutManager
        fr_v_recycler.adapter = vehicleAdapter
    }

    private fun addListener() {
        fr_v_add.setOnClickListener {
            (activity as AppCompatActivity).replaceFragment(VehicleAddFragment.newInstance())
        }
    }

    private fun addObversable() {
        viewModel.vehicleResponseMutable.observe(viewLifecycleOwner,
            Observer<Response<List<Vehicle>>> { response ->
                response.body()?.let {
                    vehicleAdapter.updateItems(ArrayList(it))
                }
            })
    }

}