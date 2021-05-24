package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class OszczedzajCele : AppCompatActivity() {

    private var btAkceptujCele: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oszczedzaj_cele)
        btAkceptujCele = findViewById(R.id.btAkceptujCele)

        btAkceptujCele?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityBackOszczedzanie()
            }
        })

    }

    private fun openActivityBackOszczedzanie(){
        val intent = Intent(this, Oszczedzaj::class.java)
        startActivity(intent)
    }
}