package com.seminario2.mecanicaapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.model.Car
import com.seminario2.mecanicaapp.ui.adapter.CarAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_main.*

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var carAdapter: CarAdapter

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fr_dash_name.text = getString(R.string.hola_name, "Pedro")
        loadCars()
    }

    private fun loadCars() {
        val mList = ArrayList<Car>()
        mList.add(Car("Fiat", "Uno", 2009, 2))
        mList.add(Car("volkswagen", "Golf", 2018, 1))
        mList.add(Car("renault", "Torino", 2013, 3))
        mList.add(Car("peugeot", "208", 2017))
        mList.add(Car("ford", "Focus", 2020))
        carAdapter = CarAdapter { car ->
            Log.i("test", "Click on Item Car = $car")
            (activity as AppCompatActivity).replaceFragment(MainFragment.newInstance(car))
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fr_dash_recycler.isNestedScrollingEnabled = false
        fr_dash_recycler.layoutManager = layoutManager
        fr_dash_recycler.adapter = carAdapter
        carAdapter.updateItems(mList)
    }

}