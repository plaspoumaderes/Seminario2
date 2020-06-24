package com.seminario2.mecanicaapp.ui.garage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.commons.extension.round
import com.seminario2.mecanicaapp.model.GarageModel

class GarageAdapter(val onClickAction: ((GarageModel) -> Unit)) :
    RecyclerView.Adapter<GarageAdapter.MyViewHolder>() {

    private var mList: ArrayList<GarageModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_garage, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mContainer.setOnClickListener {
            onClickAction(mList[position])
        }
        holder.name.text = mList[position].garageName
        holder.address.text = "${mList[position].distance} - ${mList[position].garageAddress}"
        holder.capacity.text = mList[position].garageVehicleCapacity.toString()
        holder.stars.text = "${mList[position].stars.toString()}"
        holder.email.text = mList[position].garageEmail
    }


    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val mContainer: ConstraintLayout = v.findViewById(R.id.item_garage)
        val name: TextView = v.findViewById(R.id.item_garage_name)
        val address: TextView = v.findViewById(R.id.item_garage_address)
        val capacity: TextView = v.findViewById(R.id.item_garage_capacity)
        val stars: TextView = v.findViewById(R.id.item_garage_stars)
        val email: TextView = v.findViewById(R.id.item_garage_email)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun updateItems(mList: ArrayList<GarageModel>) {
        this.mList = mList
        notifyDataSetChanged()
    }

}