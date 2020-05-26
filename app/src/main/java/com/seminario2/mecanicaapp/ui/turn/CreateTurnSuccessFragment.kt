package com.seminario2.mecanicaapp.ui.turn

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.commons.extension.replaceFragment
import com.seminario2.mecanicaapp.model.FixModelResponse
import com.seminario2.mecanicaapp.ui.DashboardFragment
import com.seminario2.mecanicaapp.ui.base.BaseResponseFragment
import retrofit2.Response

class CreateTurnSuccessFragment : BaseResponseFragment() {

    companion object {
        fun newInstance() : CreateTurnSuccessFragment {
            return CreateTurnSuccessFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.postInsertFixMutable = MutableLiveData<Response<FixModelResponse>>()
    }
    override fun getStatus(): Status {
        return Status.SUCCESS
    }

    override fun getTitle(): String {
        return getString(R.string.create_turn_title)
    }

    override fun getDescripcion(): String {
        return getString(R.string.create_turn_desc)
    }

    override fun getPrimaryButtonText(): String? {
        return getString(R.string.gohome)
    }

    override fun getPrimaryButtonListener(): View.OnClickListener? {
        return View.OnClickListener {
            (activity as AppCompatActivity).replaceFragment(DashboardFragment.newInstance(), false)
        }
    }
}