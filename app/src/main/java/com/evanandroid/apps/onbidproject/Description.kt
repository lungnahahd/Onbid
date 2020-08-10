package com.evanandroid.apps.onbidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Description : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        var title : TextView = findViewById(R.id.titleT_do)
        var num : TextView = findViewById(R.id.numT_do)
        var start : TextView = findViewById(R.id.startD_do)
        var end : TextView = findViewById(R.id.endD_do)
        var use : TextView = findViewById(R.id.useDes_do)
        val intent : Intent = getIntent()

        title.setText(intent.getStringExtra("num"))
        num.setText(intent.getStringExtra("name"))
        start.setText(intent.getStringExtra("start"))
        end.setText(intent.getStringExtra("end"))
        use.setText(intent.getStringExtra("use"))
    }
}