package com.seminario2.mecanicaapp.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.seminario2.mecanicaapp.SigaApplication
import com.seminario2.mecanicaapp.model.LoginResponse
import com.seminario2.mecanicaapp.viewmodel.SigaViewModel

abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    protected lateinit var viewModel: SigaViewModel
    protected lateinit var loginResponse: LoginResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            (it.application as SigaApplication).loginResponse?.let { lgResp ->
                loginResponse = lgResp
            }
            viewModel = ViewModelProviders.of(it)[SigaViewModel::class.java]
        }
    }
}