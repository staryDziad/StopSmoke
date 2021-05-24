package com.example.stopsmoke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Ochota_na_papierosa : AppCompatActivity() {

    private var btBardzo : Button? = null
    private var btStrasznie : Button? = null
    private var btTak : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ochota_na_papierosa)
        btBardzo = findViewById(R.id.btBardzo)
        btStrasznie = findViewById(R.id.btStrasznie)
        btTak = findViewById(R.id.btTak)
    }
}