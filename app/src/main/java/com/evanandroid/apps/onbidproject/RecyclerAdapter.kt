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
    var open_do : String? = null
    var give_do : String? = null
    var num2_do : String? = null
    var kind_do : String? = null
    var buystate_do : String? = null
    var seller_do : String? = null
    var buymethod_do : String? = null
    var costdif_do : String? = null
    var soldmethod_do : String? = null
    var different_do : String? = null
    var address : String? = null

    init {
        itemView.setOnClickListener {
            Toast.makeText(itemView?.context, "상세정보로 이동합니다.",Toast.LENGTH_SHORT).show()
            val context : Context = itemView.getContext()
            val intent = Intent(context,Description::class.java)
            intent.putExtra("name",itemView.num.text)
            intent.putExtra("num",itemView.name.text)
            intent.putExtra("start",itemView.startDay.text)
            intent.putExtra("end",itemView.endDay.text)
            intent.putExtra("use",itemView.use.text)
            intent.putExtra("OpenD",open_do)
            intent.putExtra("GiveD",give_do)
            intent.putExtra("Num2",num2_do)
            intent.putExtra("Kind", kind_do)
            intent.putExtra("BuyState",buystate_do)
            intent.putExtra("Seller",seller_do)
            intent.putExtra("BuyMethod", buymethod_do)
            intent.putExtra("CostDif", costdif_do)
            intent.putExtra("SoldMethod", soldmethod_do)
            intent.putExtra("Different", different_do)
            intent.putExtra("Address",address)

            context.startActivity(intent)
        }
    }


    fun setData(data : Data){
        itemView.name.text = "${data.name}"
        itemView.num.text = "${data.num}"
        itemView.use.text = "${data.use}"
        itemView.startDay.text = "${data.begin}"
        itemView.endDay.text = "${data.end}"
        open_do = "${data.open_do}"
        give_do = "${data.give_do}"
        num2_do = "${data.numT2}"
        kind_do = "${data.kind}"
        buystate_do = "${data.buyState}"
        seller_do = "${data.seller}"
        buymethod_do = "${data.buyMethod}"
        costdif_do = "${data.costDif}"
        soldmethod_do = "${data.soldMethod}"
        different_do = "${data.differnt}"
        address = "${data.name}"
    }



}