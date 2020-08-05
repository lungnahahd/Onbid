package com.evanandroid.apps.onbidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Xml
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycle_data.*
import org.w3c.dom.Text
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception
import java.net.URL
import java.net.URLEncoder

class RecycleData : AppCompatActivity() {
    var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        StrictMode.enableDefaults()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_data)

        //val data:MutableList<Data> = loadData() // 전체데이터를 받는 경우에 사용하는 코드

        button.setOnClickListener {

            searchData()


            var adapter = RecyclerAdapter()
            val data: MutableList<Data> = searchData()
            adapter.listData = data
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)


        }

        backBut.setOnClickListener {
            count = count - 1
            var adapter = RecyclerAdapter()
            val data: MutableList<Data> = searchData()
            adapter.listData = data
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)

        }

        firstBut.setOnClickListener {
            count = 1
            var adapter = RecyclerAdapter()
            val data: MutableList<Data> = searchData()
            adapter.listData = data
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)

        }



        goBut.setOnClickListener {
            count = count + 1
            var adapter = RecyclerAdapter()
            val data: MutableList<Data> = searchData()
            adapter.listData = data
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }


    }


    // 전체 데이터를 받는 코드
    fun loadData(): MutableList<Data> {
        val ldata: MutableList<Data> = mutableListOf()
        var b_PLNM_NM = false
        var b_PBCT_NO = false

        var plnm_nm: String? = null
        var pbct_no: String? = null

        try {
            val url =
                URL("http://openapi.onbid.co.kr/openapi/services/UtlinsttPblsalThingInquireSvc/getPublicSaleAnnouncement?serviceKey=fZrdoxTt5AoPpbAJScuxo3IeZBzRVqrhnG%2FpP7J6uZfC05FIbniTRaZicjkRyJr8Tzs0RdKmFnQgRFUNPUyXDA%3D%3D\n")
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()

            parser.setInput(url.openStream(), null)
            var parserEvent: Int = parser.getEventType()
            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                when (parserEvent) {
                    XmlPullParser.START_TAG
                    -> {
                        if (parser.name == "PBCT_NO") {
                            b_PBCT_NO = true
                        }
                        if (parser.name == "PLNM_NM") {
                            b_PLNM_NM = true
                        }
                    }
                    XmlPullParser.TEXT
                    -> {
                        if (b_PBCT_NO) {
                            pbct_no = parser.text
                            b_PBCT_NO = false
                        }
                        if (b_PLNM_NM) {
                            plnm_nm = parser.text
                            b_PLNM_NM = false
                        }
                    }

                    XmlPullParser.END_TAG
                    -> if (parser.name == "item") {
                        var data = Data(pbct_no, plnm_nm)
                        ldata.add(data)
                    }
                }
                parserEvent = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ldata
    }

    fun searchData(): MutableList<Data> {
        val ldata: MutableList<Data> = mutableListOf()

        var str: String = edit.text.toString()
        var location: String = URLEncoder.encode(str, "UTF-8")

        val queryUrl =
            "http://openapi.onbid.co.kr/openapi/services/UtlinsttPblsalThingInquireSvc/getPublicSaleAnnouncement?PLNM_NM=${location}&pageNo="
        val queryUrl2 =
            "&serviceKey=fZrdoxTt5AoPpbAJScuxo3IeZBzRVqrhnG%2FpP7J6uZfC05FIbniTRaZicjkRyJr8Tzs0RdKmFnQgRFUNPUyXDA%3D%3D"
       // var count = 1

        //val finalUrl = queryUrl + location + queryUrl


        try {

            var plnm_nm: String? = null
            var pbct_no: String? = null
            var url = URL(queryUrl + count + queryUrl2)
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(url.openStream(), "UTF-8")
            var eventType: Int = parser.getEventType()


            var tag: String

            while (eventType != XmlPullParser.END_DOCUMENT) {

                when (eventType) {

                    XmlPullParser.START_TAG
                    -> {
                        tag = parser.name
                        if (tag == "item") {
                        } else if (tag == "PBCT_NO") {
                            parser.next()
                            pbct_no = parser.text
                        } else if (tag == "PLNM_NM") {
                            parser.next()
                            plnm_nm = parser.text
                        }
                    }

                    XmlPullParser.END_TAG
                    -> if (parser.name == "item") {
                        var data = Data(pbct_no, plnm_nm)
                        ldata.add(data)
                    }

                }
                eventType = parser.next()
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
        //Toast.makeText(this, "검색이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        return ldata
    }

}