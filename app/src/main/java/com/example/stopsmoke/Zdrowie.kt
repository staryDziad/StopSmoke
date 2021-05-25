package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Zdrowie : AppCompatActivity() {


    private var btDodajWynik: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zdrowie)
        btDodajWynik = findViewById(R.id.btDodajWynik)

        btDodajWynik?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                openActivityDodajWynik()
            }
        })
    }


    private fun openActivityDodajWynik() {
        val intentA = Intent(this, ZdrowieDodajWynik::class.java)
        startActivity(intentA)
    }
}