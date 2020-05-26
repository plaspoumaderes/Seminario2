package com.seminario2.mecanicaapp.ui.turn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.commons.extension.gone
import com.seminario2.mecanicaapp.commons.extension.setImageCar
import com.seminario2.mecanicaapp.commons.extension.visible
import com.seminario2.mecanicaapp.model.FixModelResponse
import com.seminario2.mecanicaapp.model.GarageModel
import com.seminario2.mecanicaapp.model.Vehicle

class ServicesAdapter(val onClickAction: ((FixModelResponse) -> Unit)) :
    RecyclerView.Adapter<ServicesAdapter.MyViewHolder>() {

    private var mList: ArrayList<FixModelResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_services, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val service = mList[position]
        holder.mContainer.setOnClickListener {
            onClickAction(service)
        }
        holder.name.text = mList[position].garage?.garageName
        service.fixIngress?.let { date ->
            val month = date.month + 1
            holder.day.text = "${service.fixIngress?.date}/${month} ${date.hours}hs"
        }
        holder.address.text = service.garage?.garageAddress
        holder.car.text = service.vehicle.toString() + " - ${service.getStatusName()}"
        if (position == 0) {
            holder.separator.gone()
        } else {
            holder.separator.visible()
        }
    }


    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val mContainer: ConstraintLayout = v.findViewById(R.id.item_services)
        val name: TextView = v.findViewById(R.id.item_services_garage_name)
        val day: TextView = v.findViewById(R.id.item_services_day)
        val address: TextView = v.findViewById(R.id.item_services_address)
        val car: TextView = v.findViewById(R.id.item_services_car)
        val separator: View = v.findViewById(R.id.item_services_sep)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun updateItems(mList: ArrayList<FixModelResponse>) {
        this.mList = mList
        notifyDataSetChanged()
    }

}