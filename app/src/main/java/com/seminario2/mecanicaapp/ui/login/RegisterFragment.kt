package com.seminario2.mecanicaapp.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.seminario2.mecanicaapp.MainActivity
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.commons.extension.gone
import com.seminario2.mecanicaapp.commons.extension.visible
import com.seminario2.mecanicaapp.model.LoginResponse
import com.seminario2.mecanicaapp.model.RegisterModel
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
                    response.body()?.let { (activity as MainActivity).logUser(it) }
                } else {
                    fr_reg_loading.gone()
                    Toast.makeText(activity, response.message(), Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun addListener() {
        fr_register_btn.setOnClickListener {
            if (checkFields()) {
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

    private fun checkFields(): Boolean {
        return checkField(fr_register_name) &&
                checkField(fr_register_email) &&
                checkField(fr_register_user) &&
                checkField(fr_register_passwd) &&
                checkField(fr_register_address)
    }

    private fun checkField(textInput: TextInputEditText): Boolean {
        val incomplete = fr_register_name.text.toString().trim().isEmpty()
        if (incomplete) {
            textInput.error = getString(R.string.error_reguster)
        } else {
            textInput.error = null
        }
        return !incomplete
    }

}