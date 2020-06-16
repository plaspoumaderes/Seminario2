package com.seminario2.mecanicaapp.ui.turn

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.commons.extension.gone
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.commons.extension.visible
import com.seminario2.mecanicaapp.commons.listener.OnBackPressedListener
import com.seminario2.mecanicaapp.model.FixModel
import com.seminario2.mecanicaapp.model.GarageCategoryModel
import com.seminario2.mecanicaapp.model.GarageModel
import com.seminario2.mecanicaapp.ui.DashboardFragment
import com.seminario2.mecanicaapp.ui.garage.GarageAdapter
import kotlinx.android.synthetic.main.fragment_create_turn.*
import kotlinx.android.synthetic.main.fragment_select_garage.*
import java.util.*
import kotlin.collections.ArrayList

class SelectGarageFragment : BaseFragment(R.layout.fragment_select_garage), OnBackPressedListener {

    private lateinit var garageAdapter: GarageAdapter
    private lateinit var fixModel: FixModel
    private var garageList: List<GarageModel> = ArrayList()
    private var garageSelected: GarageModel? = null
    private var date: OnDateSetListener? = null
    private var myCalendar: Calendar? = null

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

    override fun onResume() {
        super.onResume()
        val array = ArrayList<Int>().apply { this.add(fixModel.fixStatusNumber) }
        viewModel.getGaragesByCategory(GarageCategoryModel(array, loginResponse.address))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurateCalendar()
        addListener()
        addObservable()
        loadAdapter()
    }

    private fun addObservable() {
        viewModel.garageByCategoriaResponseMutable.observe(viewLifecycleOwner,
            Observer { response ->
                response.body()?.let { mList ->
                    garageList = mList
                    activity?.let { act ->
                        garageAdapter.updateItems(ArrayList(mList))
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
                myCalendar?.let { calendar ->
                    DatePickerDialog(
                        act,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
        }
        fr_sl_gar_spinner.setOnClickListener {
            fr_sl_garage.visible()
        }
        fr_sl_gar_btn.setOnClickListener {
            viewModel.postInsertFix(fixModel)
        }

        fr_sl_gar_tab.homeListener = View.OnClickListener {
            (activity as AppCompatActivity).replaceFragment(DashboardFragment.newInstance(), false)
        }
    }

    private fun configurateCalendar() {
        myCalendar = Calendar.getInstance().apply {
            date = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                this.set(Calendar.YEAR, year)
                this.set(Calendar.MONTH, monthOfYear)
                this.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                fr_sl_gar_dia_value.text = "$dayOfMonth/${monthOfYear + 1}"
                fixModel.fixIngress = this.time
                showHourSpinner()
            }
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

    private fun loadAdapter() {
        garageAdapter = GarageAdapter { garageModel ->
            fixModel.garageId = garageModel._id
            fr_sl_gar_address.text = "${garageModel.distance} - ${garageModel.garageAddress}"
            fr_sl_gar_spinner.text = garageModel.garageName
            fr_sl_garage.gone()
        }
        val layoutManagerHistory = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fr_sl_garage.isNestedScrollingEnabled = false
        fr_sl_garage.layoutManager = layoutManagerHistory
        fr_sl_garage.adapter = garageAdapter
    }

    override fun onBackPressed(): Boolean {
        val useBackPress = fr_sl_garage.visibility == View.VISIBLE
        if (useBackPress) {
            fr_sl_garage.gone()
        }
        return useBackPress
    }
}