package com.evanandroid.apps.onbidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.android.synthetic.main.itemdata.view.*
import org.w3c.dom.Text

class Description : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        var getaddress : String? = null
        check = 1


        var title : TextView = findViewById(R.id.titleT_do)
        var num : TextView = findViewById(R.id.numT_do)
        var start : TextView = findViewById(R.id.startD_do)
        var end : TextView = findViewById(R.id.endD_do)
        var use : TextView = findViewById(R.id.useDes_do)
        var openD : TextView = findViewById(R.id.openD_do)
        var giveD : TextView = findViewById(R.id.giveD_do)
        var num2 : TextView = findViewById(R.id.numT2_do)
        var kind : TextView = findViewById(R.id.kind_do)
        var buystate : TextView = findViewById(R.id.buyState_do)
        var seller : TextView = findViewById(R.id.seller)
        var buymethod : TextView = findViewById(R.id.buyMethod_do)
        var costdif : TextView = findViewById(R.id.costDif_do)
        var soldmethod : TextView = findViewById(R.id.soldMethod_do)
        var different : TextView = findViewById(R.id.different_do)


        val intent : Intent = getIntent()

        getaddress = intent.getStringExtra("num")

        title.setText(intent.getStringExtra("num"))
        num.setText(intent.getStringExtra("name"))
        start.setText(intent.getStringExtra("start"))
        end.setText(intent.getStringExtra("end"))
        use.setText(intent.getStringExtra("use"))
        openD.setText(intent.getStringExtra("OpenD"))
        giveD.setText(intent.getStringExtra("GiveD"))
        num2.setText(intent.getStringExtra("Num2"))
        kind.setText(intent.getStringExtra("Kind"))
        buystate.setText(intent.getStringExtra("BuyState"))
        seller.setText(intent.getStringExtra("Seller"))
        buymethod.setText(intent.getStringExtra("BuyMethod"))
        costdif.setText(intent.getStringExtra("CostDif"))
        soldmethod.setText(intent.getStringExtra("SoldMethod"))
        different.setText(intent.getStringExtra("Different"))



        goMap.setOnClickListener {
            val intent = Intent(this, GoogleMap::class.java)
            intent.putExtra("GoAddress",getaddress)
            check = -1
            startActivity(intent)
        }
    }
}