package com.seminario2.mecanicaapp.ui.turn

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.lifecycle.Observer
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.model.Vehicle
import kotlinx.android.synthetic.main.fragment_create_turn.*
import retrofit2.Response
import java.util.*

class CreateTurnFragment : BaseFragment(R.layout.fragment_create_turn) {

    companion object {
        fun newInstance(): CreateTurnFragment {
            return CreateTurnFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListener()
        addObservable()
    }

    private fun addObservable() {
        viewModel.vehicleResponseMutable.observe(viewLifecycleOwner,
            Observer<Response<List<Vehicle>>> { response ->
                val data = ArrayList<String>()
                response.body()?.forEach {
                    data.add("${it.vehiclePlate} - ${it.vehicleModel}")
                }
                activity?.let { act ->
                    val adapter =
                        ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, data)
                    fr_cr_turn_vehiculos_input.adapter = adapter
                }
            })

    }

    fun addListener() {
        fr_cr_turn_btn.setOnClickListener {
            if (checkFields()) {

            }
        }
    }

    private fun checkFields(): Boolean {
        return checkField(fr_cr_turn_km_input) &&
                // checkField(fr_cr_turn_necesidad_input) &&
                // checkField(fr_cr_turn_taller_input) &&
                // checkField(fr_cr_turn_horario_day) &&
                checkField(fr_cr_turn_detail_input)
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

}