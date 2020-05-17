package com.seminario2.mecanicaapp.ui.turn

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.commons.extension.visible
import kotlinx.android.synthetic.main.fragment_create_turn.*
import java.util.*
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.model.Vehicle
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Response
import androidx.lifecycle.Observer

class SelectGarageFragment : BaseFragment(R.layout.fragment_select_garage) {

    private lateinit var date: OnDateSetListener
    private lateinit var myCalendar: Calendar

    companion object {
        fun newInstance(): SelectGarageFragment {
            return SelectGarageFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        configurateCalendar()
//        addListener()
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

//    private fun loadTalleres() {
//        activity?.let { act ->
//            val data = arrayOf("09:00", "10:00", "12:00", "13:00", "16:00", "18:00")
//            val adapter = ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, data)
//            fr_cr_turn_taller_input.adapter = adapter
//        }
//    }

//    fun addListener() {
//        fr_cr_turn_horario_day.setOnClickListener {
//            activity?.let { act ->
//                DatePickerDialog(
//                    act,
//                    date,
//                    myCalendar.get(Calendar.YEAR),
//                    myCalendar.get(Calendar.MONTH),
//                    myCalendar.get(Calendar.DAY_OF_MONTH)
//                ).show()
//            }
//        }
//        fr_cr_turn_btn.setOnClickListener {
//            if (checkFields()) {
//
//            }
//        }
//    }

//    private fun configurateCalendar() {
//        myCalendar = Calendar.getInstance()
//        date = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//            myCalendar.set(Calendar.YEAR, year)
//            myCalendar.set(Calendar.MONTH, monthOfYear)
//            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//            fr_cr_turn_horario_day.text = "$dayOfMonth/$monthOfYear"
//            showHourSpinner()
//        }
//    }

//    private fun showHourSpinner() {
//        fr_cr_turn_horario_spinner.visible()
//        activity?.let { act ->
//            val data = arrayOf("09:00", "10:00", "12:00", "13:00", "16:00", "18:00")
//            val adapter = ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, data)
//            fr_cr_turn_horario_spinner.adapter = adapter
//        }
//    }

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