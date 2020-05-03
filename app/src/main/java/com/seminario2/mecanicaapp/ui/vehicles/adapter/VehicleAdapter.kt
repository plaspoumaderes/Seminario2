package com.seminario2.mecanicaapp.ui.vehicles.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.commons.extension.setImageCar
import com.seminario2.mecanicaapp.model.Vehicle

class VehicleAdapter(val onClickAction: ((Vehicle) -> Unit)) :
    RecyclerView.Adapter<VehicleAdapter.MyViewHolder>() {

    private var mList: ArrayList<Vehicle> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mContainer.setOnClickListener {
            onClickAction(mList[position])
        }
        holder.image.setImageCar(mList[position].vehicleBrand)
        holder.modelo.text = mList[position].vehicleModel
        holder.year.text = mList[position].vehicleYear.toString()
    }


    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val mContainer: ConstraintLayout = v.findViewById(R.id.item_car)
        val image: ImageView = v.findViewById(R.id.item_car_img)
        val modelo: TextView = v.findViewById(R.id.item_car_modelo)
        val year: TextView = v.findViewById(R.id.item_car_year)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun updateItems(mList: ArrayList<Vehicle>) {
        this.mList = mList
        notifyDataSetChanged()
    }

}