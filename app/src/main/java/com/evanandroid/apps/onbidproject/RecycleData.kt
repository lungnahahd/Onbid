package com.evanandroid.apps.onbidproject

import android.content.Intent
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
    var btotal = false
    var endpage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        StrictMode.enableDefaults()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_data)



        //val data:MutableList<Data> = loadData() // 전체데이터를 받는 경우에 사용하는 코드

        button.setOnClickListener {
            searchData()
            btotal = true
            if (endpage <0) {
                Toast.makeText(this, "검색 결과가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
            } else {

                var adapter = RecyclerAdapter()
                val data: MutableList<Data> = searchData()
                adapter.listData = data
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)
            }

        }

        backBut.setOnClickListener {


            if (count == 1) {
                Toast.makeText(this, "처음 페이지 입니다.", Toast.LENGTH_SHORT).show()
            } else {
                count = count - 1
                var adapter = RecyclerAdapter()
                val data: MutableList<Data> = searchData()
                adapter.listData = data
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)
            }


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
            if (count == endpage) {
                Toast.makeText(this, "마지막 페이지 입니다.", Toast.LENGTH_SHORT).show()
            } else {

                count = count + 1
                var adapter = RecyclerAdapter()
                val data: MutableList<Data> = searchData()
                adapter.listData = data
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)
            }

        }


    }


    // 전체 데이터를 받는 코드
    /*fun loadData(): MutableList<Data> {
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
    }*/

    fun searchData(): MutableList<Data> {
        val ldata: MutableList<Data> = mutableListOf()

        var str: String = edit.text.toString()
        var location: String = URLEncoder.encode(str, "UTF-8")

        val queryUrl =
            "http://openapi.onbid.co.kr/openapi/services/UtlinsttPblsalThingInquireSvc/getPublicSaleAnnouncement?PLNM_NM=${location}&pageNo="
        val queryUrl2 =
            "&serviceKey=gC5PyKIxJcc0H6J648DKQVzSOvAA5dXIdFD%2F9JJ7jyxIb8GkmtQ%2FODKdRES10CQDHUmx%2FG7lH0%2F1HEPqSWi38w%3D%3D"


        try {

            var plnm_nm: String? = null
            var pbct_no: String? = null
            var ctgr_full: String? = null
            var pbct_begn: String? = null
            var pbct_cls: String? = null
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
                        /*if(tag == "items"){
                            parser.next()
                            if(parser.text == null){
                                endpage = -1

                            }
                        }*/
                        if (tag == "item") {
                        } else if (tag == "PBCT_NO") {
                            parser.next()
                            pbct_no = parser.text
                        } else if (tag == "PLNM_NM") {
                            parser.next()
                            plnm_nm = parser.text
                        } else if (tag == "CTGR_FULL_NM") {
                            parser.next()
                            ctgr_full = parser.text
                        } else if (tag == "PBCT_BEGN_DTM") {
                            parser.next()
                            pbct_begn = parser.text
                        } else if (tag == "PBCT_CLS_DTM") {
                            parser.next()
                            pbct_cls = parser.text
                        } else if (tag == "totalCount") {
                            if (btotal) {
                                parser.next()
                                val finalnum = parser.text.toInt()
                                if (finalnum == 0) {
                                    endpage = -1
                                } else if (finalnum % 10 == 0) {
                                    endpage = finalnum / 10
                                } else {
                                    endpage = finalnum / 10 + 1
                                }
                                btotal = false

                            }
                        }
                    }

                    XmlPullParser.END_TAG
                    -> if (parser.name == "item") {
                        var data = Data(pbct_no, plnm_nm, ctgr_full, pbct_begn, pbct_cls)
                        ldata.add(data)
                    }

                }
                eventType = parser.next()
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ldata
    }


}