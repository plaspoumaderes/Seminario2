package com.seminario2.mecanicaapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.commons.extension.setImageCar
import com.seminario2.mecanicaapp.model.Car

class CarAdapter(val onClickAction: ((Car) -> Unit)) :
    RecyclerView.Adapter<CarAdapter.MyViewHolder>() {

    private var mList: ArrayList<Car> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.mContainer.setOnClickListener {
            onClickAction(mList[position])
        }
        holder.image.setImageCar(mList[position].brand)
        holder.modelo.text = mList[position].modelo
        holder.year.text = mList[position].year.toString()
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

    fun updateItems(mList: java.util.ArrayList<Car>) {
        this.mList = mList
        notifyDataSetChanged()
    }

}