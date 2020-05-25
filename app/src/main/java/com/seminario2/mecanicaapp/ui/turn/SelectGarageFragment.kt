package com.seminario2.mecanicaapp.ui.turn

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.commons.extension.visible
import com.seminario2.mecanicaapp.model.FixModel
import com.seminario2.mecanicaapp.model.GarageModel
import kotlinx.android.synthetic.main.fragment_create_turn.*
import kotlinx.android.synthetic.main.fragment_select_garage.*
import java.util.*
import kotlin.collections.ArrayList

class SelectGarageFragment : BaseFragment(R.layout.fragment_select_garage) {

    private lateinit var fixModel: FixModel
    private var garageList: List<GarageModel> = ArrayList()
    private var garageSelected: GarageModel? = null
    private lateinit var date: OnDateSetListener
    private lateinit var myCalendar: Calendar

    companion object {
        const val FIX_MODEL = "fix-model-key"
        fun newInstance(toJson: String): SelectGarageFragment {
            return SelectGarageFragment().apply {
                arguments = Bundle().apply {
                    putString(FIX_MODEL, toJson)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getString(FIX_MODEL)?.apply {
                fixModel = Gson().fromJson(this, FixModel::class.java)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurateCalendar()
        addListener()
        addObservable()
    }

    private fun addObservable() {
        viewModel.garageResponseMutable.observe(viewLifecycleOwner,
            Observer { response ->
                response.body()?.let { mList ->
                    garageList = mList
                    activity?.let { act ->
                        val data = ArrayList<String>()
                        data.add("")
                        mList.forEach {
                            data.add("${it.garageName} - ${it.garagePhoneNumber ?: ""}")
                        }
                        val adapter = ArrayAdapter(act, android.R.layout.simple_spinner_item, data)
                        fr_sl_gar_spinner.adapter = adapter
                    }
                }
            })

        viewModel.postInsertFixMutable.observe(viewLifecycleOwner,
        Observer {
            (activity as AppCompatActivity).replaceFragment(
                CreateTurnSuccessFragment.newInstance(),
                false
            )
        })
    }

    fun addListener() {
        fr_sl_gar_dia_value.setOnClickListener {
            activity?.let { act ->
                DatePickerDialog(
                    act,
                    date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
        fr_sl_gar_spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    garageSelected = garageList[position - 1].apply {
                        fixModel.garageId = this._id
                        fr_sl_gar_address.text = this.garageAddress
                        mapView.visible()
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }
        fr_sl_gar_btn.setOnClickListener {
            viewModel.postInsertFix(fixModel)
        }
    }

    private fun configurateCalendar() {
        myCalendar = Calendar.getInstance()
        date = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            fr_sl_gar_dia_value.text = "$dayOfMonth/$monthOfYear"
            fixModel.fixIngress = myCalendar.time
            showHourSpinner()
        }
    }

    private fun showHourSpinner() {
        fr_sl_gar_dia_value_spinner.visible()
        activity?.let { act ->
            val adapter = ArrayAdapter(act, android.R.layout.simple_spinner_item, getRandomHour())
            fr_sl_gar_dia_value_spinner.adapter = adapter
        }
    }

    private fun getRandomHour(): ArrayList<String> {
        val mList = ArrayList<String>()
        var hour = 9
        while (hour < 18) {
            if ((Math.random() * 100).toInt() % 2 == 0) {
                mList.add("$hour:00")
            }
            hour++
        }
        return mList
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