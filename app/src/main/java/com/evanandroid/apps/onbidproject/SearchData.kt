package com.evanandroid.apps.onbidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.EditText
import android.widget.TextView
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import java.net.URLEncoder

//import java.lang.Override as

class SearchData : AppCompatActivity() {

    lateinit var edit: EditText
    lateinit var text: TextView
    lateinit var xpp: XmlPullParser


    lateinit var data: String


    inner class WorkerRunnable : Runnable {
        override fun run() {
            data = getXmlData()

            runOnUiThread() {
                text.setText(data)
            }

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StrictMode.enableDefaults()
        setContentView(R.layout.activity_search_data)

        edit = findViewById(R.id.edit)
        text = findViewById(R.id.result)

    }

    fun mOnClick(v: View) {
        var thread = Thread(WorkerRunnable())
        when (v.id) {
            R.id.button
            -> thread.start()



        }
    }

    fun getXmlData(): String {
        //var b_item = false
        var b_PLNM_NM = false
        var b_ORG_NM = false
        var b_PLNM_DT = false
        var b_BID_MTD_NM = false
        var b_DPSL_MTD_NM = false
        var b_PRPT_DVSN_NM = false
        var b_CTGR_FULL_NM = false
        var buffer = StringBuffer()
        var str: String = edit.text.toString()
        //한글을 받기위한 변수
        var location: String = URLEncoder.encode(str, "UTF-8")

        val queryUrl =
            "http://openapi.onbid.co.kr/openapi/services/UtlinsttPblsalThingInquireSvc/getPublicSaleAnnouncement?PLNM_NM="

        val queryUrl2 =
            "&serviceKey=fZrdoxTt5AoPpbAJScuxo3IeZBzRVqrhnG%2FpP7J6uZfC05FIbniTRaZicjkRyJr8Tzs0RdKmFnQgRFUNPUyXDA%3D%3D"
        val please = queryUrl + location + queryUrl2
        try {
            val url = URL(please)
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(url.openStream(), "UTF-8")
            var eventType: Int = parser.getEventType()
            var tag: String







            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_DOCUMENT
                    -> buffer.append("검색 시작 \n\n")

                    XmlPullParser.START_TAG
                    -> {
                        tag = parser.name
                        if (tag == "item") {
                        } else if (tag == "PLNM_NM") {
                            buffer.append("공고명 : ")
                            parser.next()
                            buffer.append(parser.text)
                            buffer.append("\n")
                        } else if (tag == "ORG_NM") {
                            buffer.append("공고 기관명 :")
                            parser.next()
                            buffer.append(parser.text)
                            buffer.append("\n")
                        } else if (tag == "PLNM_DT") {
                            buffer.append("공고일자 : ")
                            parser.next()
                            buffer.append(parser.text)
                            buffer.append("\n")
                        } else if (tag == "BID_MTD_NM") {
                            buffer.append("입찰방식 : ")
                            parser.next()
                            buffer.append(parser.text)
                            buffer.append("\n")
                        } else if (tag == "DPSL_MTD_NM") {
                            buffer.append("처분방식 : ")
                            parser.next()
                            buffer.append(parser.text)
                            buffer.append("\n")
                        } else if (tag == "PRPT_DVSN_NM") {
                            buffer.append("재산구분 : ")
                            parser.next()
                            buffer.append(parser.text)
                            buffer.append("\n")
                        } else if (tag == "CTGR_FULL_NM") {
                            buffer.append("용도 : ")
                            parser.next()
                            buffer.append(parser.text)
                            buffer.append("\n")
                        }
                    }

                    XmlPullParser.END_TAG
                    -> {
                        tag = parser.name
                        if(tag.equals("item")){
                            buffer.append("\n")
                        }
                    }


                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        buffer.append("조회 종료")
        return buffer.toString()
    }
}

