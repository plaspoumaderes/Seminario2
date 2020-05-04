package com.seminario2.mecanicaapp.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
import com.seminario2.mecanicaapp.ui.DashboardFragment
import com.seminario2.mecanicaapp.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Response

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewModel: LoginViewModel

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
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
        viewModel.loginResponseMutable.observe(
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
                    (activity?.application as SigaApplication).loginResponse = response.body()
                    (activity as AppCompatActivity).replaceFragment(
                        DashboardFragment.newInstance(),
                        false
                    )
                } else {
                    fr_login_loading.gone()
                    Toast.makeText(activity, "Usuario y/o Contraseña invalida", Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun addListener() {
        fr_login_btn.setOnClickListener {
            fr_login_loading.visible()
            val user: String = fr_login_user.text.toString().trim()
            val passwd: String = fr_login_passwd.text.toString().trim()
            viewModel.login(user, passwd)
        }
        fr_login_register.setOnClickListener {
            (activity as AppCompatActivity).replaceFragment(RegisterFragment.newInstance())
        }
    }


}