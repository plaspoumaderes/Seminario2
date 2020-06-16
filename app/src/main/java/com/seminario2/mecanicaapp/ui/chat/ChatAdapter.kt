package com.seminario2.mecanicaapp.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.seminario2.mecanicaapp.R
import com.seminario2.mecanicaapp.commons.extension.gone
import com.seminario2.mecanicaapp.commons.extension.setImageCar
import com.seminario2.mecanicaapp.model.ChatModel
import com.seminario2.mecanicaapp.model.GarageModel
import com.seminario2.mecanicaapp.model.Vehicle

class ChatAdapter(val fullName: String, val garageModel: GarageModel?) :
    RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    private var mList: ArrayList<ChatModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val chatModel = mList[position]
        if (chatModel.fullName == fullName) {
            holder.name.gone()
            val params = holder.cardView.layoutParams
        } else {
            holder.name.text = garageModel?.garageName
        }
        holder.body.text = chatModel.body
    }

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val cardView: CardView = v.findViewById(R.id.item_chat)
        val name: TextView = v.findViewById(R.id.item_chat_name)
        val body: TextView = v.findViewById(R.id.item_chat_body)
    }

    override fun getItemCount(): Int {
        return mList.count()
    }

    fun updateItems(mList: ArrayList<ChatModel>) {
        this.mList = mList
        notifyDataSetChanged()
    }

}