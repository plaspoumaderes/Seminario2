package com.seminario2.mecanicaapp.ui.garage

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.gson.Gson
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.base.BaseFragment
import com.seminario2.mecanicaapp.commons.extension.visible
import com.seminario2.mecanicaapp.model.Comment
import com.seminario2.mecanicaapp.model.GarageModel
import com.seminario2.mecanicaapp.utils.SharedPreference
import kotlinx.android.synthetic.main.fragment_evaluate.*

class EvaluateFragment : BaseFragment(R.layout.fragment_evaluate) {

    private var fixId: String = ""
    private var runnable: Runnable? = null
    private var handler: Handler? = null
    private lateinit var garageModel: GarageModel
    private lateinit var comment: Comment

    companion object {
        const val GARAGE_KEY = "garage-key"
        const val FIX_ID = "fixId-key"
        fun newInstance(
            garageModel: GarageModel,
            fixId: String
        ): EvaluateFragment {
            return EvaluateFragment().apply {
                arguments = Bundle().apply {
                    putString(GARAGE_KEY, Gson().toJson(garageModel))
                    putString(FIX_ID, fixId)
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            garageModel = Gson().fromJson(it.getString(GARAGE_KEY), GarageModel::class.java).apply {
                fr_ev_name.text = this.garageName
            }
            it.getString(FIX_ID)?.let {
                fixId = it
            }
        }
        comment = Comment(garageModel._id, loginResponse.userName)
        addListener()
    }

    private fun addListener() {
        fr_ev_star_1.setOnClickListener {
            comment.stars = 1
            fr_ev_btn.isEnabled = true
            fr_ev_star_1.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_2.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
            fr_ev_star_3.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
            fr_ev_star_4.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
            fr_ev_star_5.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
        }
        fr_ev_star_2.setOnClickListener {
            comment.stars = 2
            fr_ev_btn.isEnabled = true
            fr_ev_star_1.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_2.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_3.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
            fr_ev_star_4.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
            fr_ev_star_5.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
        }
        fr_ev_star_3.setOnClickListener {
            comment.stars = 3
            fr_ev_btn.isEnabled = true
            fr_ev_star_1.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_2.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_3.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_4.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
            fr_ev_star_5.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
        }
        fr_ev_star_4.setOnClickListener {
            comment.stars = 4
            fr_ev_btn.isEnabled = true
            fr_ev_star_1.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_2.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_3.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_4.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_5.setImageDrawable(resources.getDrawable(R.drawable.ic_star_border))
        }
        fr_ev_star_5.setOnClickListener {
            comment.stars = 5
            fr_ev_btn.isEnabled = true
            fr_ev_star_1.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_2.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_3.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_4.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
            fr_ev_star_5.setImageDrawable(resources.getDrawable(R.drawable.ic_star))
        }
        fr_ev_btn.setOnClickListener {
            fr_ev_loading.visible()
            comment.title = fr_ev_input_title.text.toString()
            comment.body = fr_ev_input.text.toString()
            viewModel.postComment(comment)
            activity?.let { act -> SharedPreference(act).save(fixId, true) }
            handler = Handler().apply {
                runnable = Runnable {
                    activity?.onBackPressed()
                }
                this.postDelayed(runnable, 2000)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        handler?.removeCallbacks(runnable)
    }
}