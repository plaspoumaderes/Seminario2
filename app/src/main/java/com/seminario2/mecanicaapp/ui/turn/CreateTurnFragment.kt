package com.seminario2.mecanicaapp.ui.turn

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.model.FixModel
import com.seminario2.mecanicaapp.model.Vehicle
import com.seminario2.mecanicaapp.ui.DashboardFragment
import kotlinx.android.synthetic.main.fragment_create_turn.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.lang.Exception
import kotlin.collections.ArrayList

class CreateTurnFragment : BaseFragment(R.layout.fragment_create_turn),
    AdapterView.OnItemSelectedListener {

    private var vehicleList = ArrayList<Vehicle>()
    private lateinit var fixModel: FixModel

    companion object {
        fun newInstance(): CreateTurnFragment {
            return CreateTurnFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fixModel = FixModel("", loginResponse.userName, "", "", "")
        fixModel.fullName = loginResponse.fullName
        fixModel.address = loginResponse.address
        addListener()
        addObservable()
    }

    private fun addObservable() {
        viewModel.vehicleResponseMutable.observe(viewLifecycleOwner,
            Observer { response ->
                val data = ArrayList<String>()
                data.add("")
                response.body()?.let {
                    vehicleList = ArrayList(it)
                    it.forEach { v ->
                        data.add("${v.vehiclePlate} - ${v.vehicleModel}")
                    }
                }
                activity?.let { act ->
                    val adapter =
                        ArrayAdapter(act, android.R.layout.simple_spinner_item, data)
                    fr_cr_turn_vehiculos_input.adapter = adapter
                }
            })

    }

    private fun addListener() {
        if (fr_cr_turn_vehiculos_input != null) {
            fr_cr_turn_vehiculos_input.onItemSelectedListener = this
        }
        fr_cr_turn_btn.setOnClickListener {
            if (checkFields()) {
                fixModel.fixType = fr_cr_turn_necesidad_input.selectedItem.toString().trim()
                (activity as AppCompatActivity).replaceFragment(
                    SelectGarageFragment.newInstance(
                        Gson().toJson(fixModel)
                    )
                )
            }
        }

        fr_cr_turn_km_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                fixModel.fixVehicleKm = p0.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
        fr_cr_turn_tab.homeListener = View.OnClickListener {
            (activity as AppCompatActivity).replaceFragment(DashboardFragment.newInstance(), false)
        }
    }

    private fun checkFields(): Boolean {
        return checkField(fr_cr_turn_km_input) &&
                checkField(fr_cr_turn_vehiculos_input) &&
                checkField(fr_cr_turn_necesidad_input)
    }

    private fun checkField(textInput: EditText): Boolean {
        val incomplete = textInput.text.toString().trim().isEmpty()
        if (incomplete) {
            textInput.error = getString(R.string.error_reguster)
        } else {
            textInput.error = null
        }
        return !incomplete
    }

    private fun checkField(textInput: Spinner): Boolean {
        val incomplete = textInput.selectedItem.toString().trim().isEmpty()
        return !incomplete
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        fixModel.vehicleId = ""
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, position: Long) {
        try {
            if (position > 0) {
                vehicleList[position.toInt() - 1]._id?.let { id ->
                    fixModel.vehicleId = id
                }
            }
        } catch (e: Exception) {

        }

    }

}