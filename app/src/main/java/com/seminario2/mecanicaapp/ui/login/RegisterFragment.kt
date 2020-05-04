package com.seminario2.mecanicaapp.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.SigaApplication
import com.seminario2.mecanicaapp.commons.constants.Constants
import com.seminario2.mecanicaapp.commons.extension.gone
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.commons.extension.visible
import com.seminario2.mecanicaapp.model.LoginResponse
import com.seminario2.mecanicaapp.model.RegisterModel
import com.seminario2.mecanicaapp.ui.DashboardFragment
import com.seminario2.mecanicaapp.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import retrofit2.Response

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var viewModel: LoginViewModel

    companion object {
        fun newInstance(): RegisterFragment {
            return RegisterFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProviders.of(it)[LoginViewModel::class.java]
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListener()
        addObservableView()
    }

    private fun addObservableView() {
        viewModel.registerResponseMutable.observe(
            viewLifecycleOwner,
            Observer<Response<LoginResponse>> { response ->
                if (response.isSuccessful) {
                    activity?.getSharedPreferences(Constants.SIGA_PREFS, Context.MODE_PRIVATE)
                        ?.let {
                            it.edit().apply {
                                putString(Constants.USER_ID, Gson().toJson(response.body()))
                                apply()
                            }
                        }
                    response.body()?.let {
                        (activity?.application as SigaApplication).loginResponse = it
                    }
                    activity?.supportFragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    (activity as AppCompatActivity).replaceFragment(DashboardFragment.newInstance(), false)
                } else {
                    fr_reg_loading.gone()
                    Toast.makeText(activity, response.message(), Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun addListener() {
        fr_register_btn.setOnClickListener {
            fr_reg_loading.visible()
            viewModel.register(
                RegisterModel(
                    fr_register_name.text.toString().trim(),
                    fr_register_email.text.toString().trim(),
                    fr_register_user.text.toString().trim(),
                    fr_register_passwd.text.toString().trim(),
                    fr_register_passwd.text.toString().trim(),
                    fr_register_address.text.toString().trim()
                )
            )
        }
    }


}