package com.evanandroid.apps.onbidproject

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

var check : Int = 1
class GoogleMap : FragmentActivity(), OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var geocoder: Geocoder? = null
    private var button: Button? = null
    private var editText: EditText? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_googlemap)
        editText = findViewById<View>(R.id.editText) as EditText
        button = findViewById<View>(R.id.button) as Button
        val intent : Intent = getIntent()
        var str = intent.getStringExtra("GoAddress")
        editText!!.setText(str)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        geocoder = Geocoder(this)

        // 맵 터치 이벤트 구현 //
        mMap!!.setOnMapClickListener { point ->
            val mOptions = MarkerOptions()
            // 마커 타이틀
            mOptions.title("마커 좌표")
            val latitude = point.latitude // 위도
            val longitude = point.longitude // 경도
            // 마커의 스니펫(간단한 텍스트) 설정
            mOptions.snippet("$latitude, $longitude")
            // LatLng: 위도 경도 쌍을 나타냄
            mOptions.position(LatLng(latitude, longitude))
            // 마커(핀) 추가
            googleMap.addMarker(mOptions)
        }
        ////////////////////

        // 버튼 이벤트
        button!!.setOnClickListener {

            var str = editText!!.text.toString()

            var addressList: List<Address>? = null
            try {
                //editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                addressList = geocoder!!.getFromLocationName(
                   str,  // 주소
                    10
                ) // 최대 검색 결과 개수
            } catch (e: IOException) {
                e.printStackTrace()
            }
            println(addressList!![0].toString())
            // 콤마를 기준으로 split
            val splitStr =
                addressList[0].toString().split(",".toRegex()).toTypedArray()
            val address = splitStr[0]
                .substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length - 2) // 주소
            println(address)
            val latitude =
                splitStr[10].substring(splitStr[10].indexOf("=") + 1) // 위도
            val longitude =
                splitStr[12].substring(splitStr[12].indexOf("=") + 1) // 경도
            println(latitude)
            println(longitude)

            // 좌표(위도, 경도) 생성
            val point = LatLng(latitude.toDouble(), longitude.toDouble())
            // 마커 생성
            val mOptions2 = MarkerOptions()
            mOptions2.title("search result")
            mOptions2.snippet(address)
            mOptions2.position(point)
            // 마커 추가
            mMap!!.addMarker(mOptions2)
            // 해당 좌표로 화면 줌
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
        }
        ////////////////////

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(37.5642135, 127.0016985)
        mMap!!.addMarker(MarkerOptions().position(sydney).title("대한민국"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}

