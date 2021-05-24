package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Oszczedzaj : AppCompatActivity() {

    private var btCele: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oszczedzaj)
        btCele = findViewById(R.id.btCele)

        btCele?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityCele()
            }
        })
    }

    private fun openActivityCele(){
        val intent = Intent(this, OszczedzajCele::class.java)
        startActivity(intent)
    }
}