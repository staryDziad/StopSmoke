package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Rejestracja : AppCompatActivity() {
    private var btDalej: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rejestracja)

        btDalej = findViewById(R.id.btDalej)

        btDalej?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityDaneDoRejestracji()
            }
        })
    }

    private fun openActivityDaneDoRejestracji(){
        val intent = Intent(this, DaneDoRejestracji::class.java)
        startActivity(intent)
    }
}