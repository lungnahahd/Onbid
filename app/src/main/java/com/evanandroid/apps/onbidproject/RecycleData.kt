package com.evanandroid.apps.onbidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycle_data.*
import org.w3c.dom.Text
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception
import java.net.URL

class RecycleData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        StrictMode.enableDefaults()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_data)
       /* var textnum : TextView = findViewById(R.id.num)
        var textname : TextView = findViewById(R.id.name)*/
        val data:MutableList<Data> = loadData()
        var adapter = RecyclerAdapter()
        adapter.listData = data
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun loadData() : MutableList<Data>{
        val ldata : MutableList<Data> = mutableListOf()
        var b_PLNM_NM = false
        var b_PBCT_NO = false
        /*var statusnum : TextView = findViewById(R.id.num)
        var statusname : TextView = findViewById(R.id.name)*/

        var plnm_nm : String? = null
        var pbct_no : String? = null

        try {
            val url = URL("http://openapi.onbid.co.kr/openapi/services/UtlinsttPblsalThingInquireSvc/getPublicSaleAnnouncement?serviceKey=fZrdoxTt5AoPpbAJScuxo3IeZBzRVqrhnG%2FpP7J6uZfC05FIbniTRaZicjkRyJr8Tzs0RdKmFnQgRFUNPUyXDA%3D%3D\n")
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()

            parser.setInput(url.openStream(), null)
            var parserEvent : Int = parser.getEventType()
            while (parserEvent != XmlPullParser.END_DOCUMENT){
                when(parserEvent){
                    XmlPullParser.START_TAG
                    ->{
                        if(parser.name == "PBCT_NO"){
                            b_PBCT_NO = true
                        }
                        if(parser.name == "PLNM_NM"){
                            b_PLNM_NM = true
                        }
                    }
                    XmlPullParser.TEXT
                    -> {
                        if(b_PBCT_NO){
                            pbct_no = parser.text
                            b_PBCT_NO = false
                        }
                        if(b_PLNM_NM){
                            plnm_nm = parser.text
                            b_PLNM_NM = false
                        }
                    }

                    XmlPullParser.END_TAG
                    -> if(parser.name=="item"){
                        var data = Data(pbct_no, plnm_nm)
                        ldata.add(data)
                    }
                }
                parserEvent = parser.next()
            }
        }catch (e : Exception){
            /*statusnum.setText("에러가 발생했습니다.")
            statusname.setText("에러가 발생했습니다.")*/
            e.printStackTrace()
        }
        return ldata
    }

}