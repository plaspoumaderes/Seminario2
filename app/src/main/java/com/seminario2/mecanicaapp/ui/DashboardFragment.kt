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
import com.seminario2.mecanicaapp.model.FixModelResponse
import com.seminario2.mecanicaapp.model.GarageModel
import com.seminario2.mecanicaapp.model.Vehicle
import com.seminario2.mecanicaapp.splash.SplashActivity
import com.seminario2.mecanicaapp.ui.garage.EvaluateFragment
import com.seminario2.mecanicaapp.ui.turn.CreateTurnFragment
import com.seminario2.mecanicaapp.ui.turn.ServicesAdapter
import com.seminario2.mecanicaapp.ui.vehicles.adapter.VehicleAdapter
import com.seminario2.mecanicaapp.ui.vehicles.VehiclesFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private var vehicleList: List<Vehicle>? = null
    private var fixList: List<FixModelResponse>? = null
    private var garageList: List<GarageModel>? = null
    private lateinit var vehicleAdapter: VehicleAdapter
    private lateinit var servicesAdapter: ServicesAdapter
    private lateinit var historyServices: ServicesAdapter

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getVehiclesbyUserName(loginResponse)
        viewModel.getFixesby(loginResponse)
        viewModel.getGarages()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fr_dash_name.text = getString(R.string.hola_name, loginResponse.fullName.toUpperCase())
        loadCars()
        loadServices()
        addListener()
        addObversable()
    }

    private fun addObversable() {
        viewModel.vehicleResponseMutable.observe(viewLifecycleOwner,
            Observer { response ->
                response.body()?.let {
                    vehicleList = it
                    if (it.size > 2) {
                        vehicleAdapter.updateItems(ArrayList(it.subList(0, 1)))
                    } else {
                        vehicleAdapter.updateItems(ArrayList(it))
                    }
                    showVisibility(it.isNotEmpty(), fr_dash_recycler, fr_dash_not_car)
                    mergeFixAndGarage()
                }
            })

        viewModel.getListFixMutable.observe(viewLifecycleOwner,
            Observer { response ->
                response.body()?.let { mList ->
                    fixList = mList
                    mergeFixAndGarage()
                }
            })

        viewModel.garageResponseMutable.observe(viewLifecycleOwner,
            Observer { response ->
                response.body()?.let { mList ->
                    garageList = mList
                    mergeFixAndGarage()
                }
            })
    }

    // Negrada por backend
    fun mergeFixAndGarage() {
        garageList?.let { listGarage ->
            fixList?.let { listFix ->
                vehicleList?.let { listVehicle ->
                    val historyList = ArrayList<FixModelResponse>()
                    val servicesList = ArrayList<FixModelResponse>()
                    listFix.forEach { fix ->
                        listGarage.forEach {
                            if (it._id == fix.garageId)
                                fix.garage = it
                        }
                        listVehicle.forEach {
                            if (it._id == fix.vehicleId[0])
                                fix.vehicle = it
                        }
                        if (fix.fixStatusNumber >= 5) {
                            historyList.add(fix)
                        } else {
                            servicesList.add(fix)
                        }
                    }
                    updateServices(servicesList, historyList)
                }
            }
        }
    }

    private fun updateServices(
        servicesList: java.util.ArrayList<FixModelResponse>,
        historyList: java.util.ArrayList<FixModelResponse>
    ) {
        if (servicesList.isEmpty()) {
            fr_dash_services_not.visible()
            fr_dash_services_recycler.gone()
        } else {
            fr_dash_services_not.gone()
            fr_dash_services_recycler.visible()
            servicesAdapter.updateItems(servicesList)
        }
        if (historyList.isEmpty()) {
            fr_dash_history_not.visible()
            fr_dash_history_recycler.gone()
        } else {
            fr_dash_history_not.gone()
            fr_dash_history_recycler.visible()
            historyServices.updateItems(historyList)
        }
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
        vehicleAdapter = VehicleAdapter { car -> Log.i(this.javaClass.name, car.toString()) }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fr_dash_recycler.isNestedScrollingEnabled = false
        fr_dash_recycler.layoutManager = layoutManager
        fr_dash_recycler.adapter = vehicleAdapter
    }


    fun loadServices() {
        servicesAdapter = ServicesAdapter { ser ->
            (activity as AppCompatActivity).replaceFragment(MainFragment.newInstance(ser))
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fr_dash_services_recycler.isNestedScrollingEnabled = false
        fr_dash_services_recycler.layoutManager = layoutManager
        fr_dash_services_recycler.adapter = servicesAdapter


        historyServices = ServicesAdapter { ser ->
            if (ser.fixStatusNumber == 5) {
                ser.garage?.let {
                    (activity as AppCompatActivity).replaceFragment(EvaluateFragment.newInstance(it))
                }
            }
        }
        val layoutManagerHistory = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fr_dash_history_recycler.isNestedScrollingEnabled = false
        fr_dash_history_recycler.layoutManager = layoutManagerHistory
        fr_dash_history_recycler.adapter = historyServices
    }

}