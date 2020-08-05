package com.evanandroid.apps.onbidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.TextView
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        StrictMode.enableDefaults()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        StrictMode.enableDefaults()
        var status : TextView = findViewById(R.id.result)

        var b_PLNM_NM = false
        var b_ORG_NM = false
        var b_PLNM_DT = false
        var b_BID_MTD_NM = false
        var b_DPSL_MTD_NM = false
        var b_PRPT_DVSN_NM = false
        var b_CTGR_FULL_NM = false

        var plnm_nm : String? = null
        var org_nm :String? = null
        var plnm_dt :String? = null
        var bid_mtd_nm : String? = null
        var dpsl_mtd_nm : String? = null
        var prpt_dvsn_nm : String? = null
        var ctgr_full_nm : String? = null

        try {
            val url = URL("http://openapi.onbid.co.kr/openapi/services/UtlinsttPblsalThingInquireSvc/getPublicSaleAnnouncement?serviceKey=fZrdoxTt5AoPpbAJScuxo3IeZBzRVqrhnG%2FpP7J6uZfC05FIbniTRaZicjkRyJr8Tzs0RdKmFnQgRFUNPUyXDA%3D%3D\n")
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()

            parser.setInput(url.openStream(), null)
            var parserEvent: Int = parser.getEventType()
            while (parserEvent != XmlPullParser.END_DOCUMENT){
                when(parserEvent){
                    XmlPullParser.START_TAG
                    ->{
                        if(parser.name == "PLNM_NM"){
                            b_PLNM_NM = true
                        }
                        if(parser.name == "ORG_NM"){
                            b_ORG_NM = true
                        }
                        if(parser.name == "PLNM_DT"){
                            b_PLNM_DT = true
                        }
                        if(parser.name == "BID_MTD_NM"){
                            b_BID_MTD_NM = true
                        }
                        if(parser.name == "DPSL_MTD_NM"){
                            b_DPSL_MTD_NM = true
                        }
                        if(parser.name == "PRPT_DVSN_NM"){
                            b_PRPT_DVSN_NM = true
                        }
                        if(parser.name == "CTGR_FULL_NM"){
                            b_CTGR_FULL_NM = true
                        }
                    }
                    XmlPullParser.TEXT
                    -> {
                        if(b_PLNM_NM){
                            plnm_nm = parser.text
                            b_PLNM_NM = false
                        }
                        if(b_ORG_NM){
                            org_nm = parser.text
                            b_ORG_NM = false
                        }
                        if(b_PLNM_DT){
                            plnm_dt = parser.text
                            b_PLNM_DT = false
                        }
                        if(b_BID_MTD_NM){
                            bid_mtd_nm = parser.text
                            b_BID_MTD_NM = false
                        }
                        if(b_DPSL_MTD_NM){
                            dpsl_mtd_nm = parser.text
                            b_DPSL_MTD_NM = false
                        }
                        if(b_PRPT_DVSN_NM){
                            prpt_dvsn_nm = parser.text
                            b_PRPT_DVSN_NM = false
                        }
                        if(b_CTGR_FULL_NM){
                            ctgr_full_nm = parser.text
                            b_CTGR_FULL_NM = false
                        }
                    }
                    XmlPullParser.END_TAG
                    -> if(parser.name == "item"){
                        val past = status.text
                        status.setText(past.toString() + "\n" +"\n" +"공고명 : ${plnm_nm}" +"\n 공고 일자 : ${plnm_dt} " + "      공고 기관명 : ${org_nm}"
                        + "\n 입찰 방식 : ${bid_mtd_nm}" + "     처분 방식 : ${dpsl_mtd_nm}" + "\n 용도 : ${ctgr_full_nm}" + "     재산 구분 : ${prpt_dvsn_nm}")
                    }
                }
                parserEvent = parser.next()
            }
        }catch (e : Exception){
            status.setText("에러가 발생했습니다.")
            e.printStackTrace()
        }
    }
}