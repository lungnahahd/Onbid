package com.evanandroid.apps.onbidproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_description.view.*
import kotlinx.android.synthetic.main.description.view.*
import kotlinx.android.synthetic.main.description.view.numText
import kotlinx.android.synthetic.main.description.view.titleText
import kotlinx.android.synthetic.main.itemdata.view.*

class RecyclerAdapter : RecyclerView.Adapter<Holder>() {
    var listData = mutableListOf<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemdata,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = listData.get(position)
        holder.setData(data)

    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

    /*init {
        itemView.setOnClickListener {
            Toast.makeText(this,"선택 : ${itemView.titleT.text}")
        }
    }
*/

    fun setData(data : Data){
        itemView.name.text = "${data.name}"
        itemView.num.text = "${data.num}"





    }

}