package com.evanandroid.apps.onbidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Description : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        var title : TextView = findViewById(R.id.titleT)
        var num : TextView = findViewById(R.id.numT)
        val intent : Intent = getIntent()

        title.setText(intent.getStringExtra("num"))
        num.setText(intent.getStringExtra("name"))
    }
}