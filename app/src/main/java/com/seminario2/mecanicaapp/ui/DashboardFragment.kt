package com.seminario2.mecanicaapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.commons.constants.Constants
import com.seminario2.mecanicaapp.commons.extension.gone
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.commons.extension.visible
import com.seminario2.mecanicaapp.model.GarageModel
import com.seminario2.mecanicaapp.model.Vehicle
import com.seminario2.mecanicaapp.splash.SplashActivity
import com.seminario2.mecanicaapp.ui.garage.GarageAdapter
import com.seminario2.mecanicaapp.ui.turn.CreateTurnFragment
import com.seminario2.mecanicaapp.ui.vehicles.adapter.VehicleAdapter
import com.seminario2.mecanicaapp.ui.vehicles.VehiclesFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Response

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private lateinit var vehicleAdapter: VehicleAdapter
    private lateinit var garageAdapter: GarageAdapter

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getVehiclesbyUserName(loginResponse)
        viewModel.getGarages()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fr_dash_name.text = getString(R.string.hola_name, loginResponse.fullName.toUpperCase())
        loadCars()
        loadGarage()
        addListener()
        addObversable()
    }

    private fun addObversable() {
        viewModel.vehicleResponseMutable.observe(viewLifecycleOwner,
            Observer<Response<List<Vehicle>>> { response ->
                response.body()?.let {
                    if (it.size > 2) {
                        vehicleAdapter.updateItems(ArrayList(it.subList(0, 1)))
                    } else {
                        vehicleAdapter.updateItems(ArrayList(it))
                    }
                    showVisibility(it.isNotEmpty(), fr_dash_recycler, fr_dash_not_car)
                }
            })

        viewModel.garageResponseMutable.observe(viewLifecycleOwner,
            Observer<Response<List<GarageModel>>> { response ->
                response.body()?.let {
                    if (it.size > 2) {
                        garageAdapter.updateItems(ArrayList(it.subList(0, 1)))
                    } else {
                        garageAdapter.updateItems(ArrayList(it))
                    }
                    showVisibility(it.isNotEmpty(), fr_dash_garage_recycler, fr_dash_garage_not)
                }
            })
    }

    private fun addListener() {
        fr_dash_vehicles.setOnClickListener {
            (activity as AppCompatActivity).replaceFragment(VehiclesFragment.newInstance())
        }
        fr_dash_logout.setOnClickListener {
            activity?.getSharedPreferences(Constants.SIGA_PREFS, Context.MODE_PRIVATE)
                ?.let {
                    it.edit().apply {
                        putString(Constants.USER_ID, null)
                        apply()
                    }
                }

            val intent = Intent(activity, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        fr_dash_tab.searchListener = View.OnClickListener {
            (activity as AppCompatActivity).replaceFragment(CreateTurnFragment.newInstance(), false)
        }
    }

    private fun showVisibility(hasItems: Boolean, recycler: RecyclerView, textView: TextView) {
        if (hasItems) {
            textView.gone()
            recycler.visible()
        } else {
            textView.visible()
            recycler.gone()
        }
    }

    private fun loadCars() {
        vehicleAdapter = VehicleAdapter { car ->
            (activity as AppCompatActivity).replaceFragment(MainFragment.newInstance(car))
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fr_dash_recycler.isNestedScrollingEnabled = false
        fr_dash_recycler.layoutManager = layoutManager
        fr_dash_recycler.adapter = vehicleAdapter
    }


    fun loadGarage() {
        garageAdapter = GarageAdapter { garage ->
            Log.i("test", "garage -> $garage")
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fr_dash_garage_recycler.isNestedScrollingEnabled = false
        fr_dash_garage_recycler.layoutManager = layoutManager
        fr_dash_garage_recycler.adapter = garageAdapter
    }

}