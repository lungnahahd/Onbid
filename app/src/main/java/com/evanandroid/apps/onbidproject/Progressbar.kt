package com.evanandroid.apps.onbidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_progressbar.*
import kotlin.concurrent.thread

class Progressbar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progressbar)


        thread(start=true) {
            Thread.sleep(1000)
            runOnUiThread {
                showProgress(false)
            }

            val intent = Intent(baseContext, RecycleData::class.java)
            startActivity(intent)

        }
    }

        fun showProgress(show : Boolean) {
            if (show) {
                progressBar4.visibility = View.VISIBLE
            } else {
                progressBar4.visibility = View.GONE
            }
        }


}