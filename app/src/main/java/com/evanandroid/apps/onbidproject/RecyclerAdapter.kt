package com.evanandroid.apps.onbidproject

import android.app.Activity
import android.content.Intent
import android.support.v4.app.INotificationSideChannel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_description.view.*
import kotlinx.android.synthetic.main.description.view.*
import kotlinx.android.synthetic.main.description.view.numText
import kotlinx.android.synthetic.main.description.view.titleText
import kotlinx.android.synthetic.main.itemdata.view.*
import java.security.AccessController.getContext
import kotlin.coroutines.coroutineContext
import android.content.Context as Context

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

    init {
        itemView.setOnClickListener {
            Toast.makeText(itemView?.context, "상세정보로 이동합니다.",Toast.LENGTH_SHORT).show()
            val context : Context = itemView.getContext()
            val intent = Intent(context,Description::class.java)
            intent.putExtra("name",itemView.name.text)
            intent.putExtra("num",itemView.num.text)
            context.startActivity(intent)

            //context.startActivity(Intent(context,Description::class.java))
        }
    }


    fun setData(data : Data){
        itemView.name.text = "${data.name}"
        itemView.num.text = "${data.num}"




    }



}