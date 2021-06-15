package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class DaneDoRejestracji : AppCompatActivity() {

    private var btZapis: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dane_do_rejestracji)

        btZapis = findViewById(R.id.btZapis)
        btZapis?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v : View){
                openActivityMain()
            }
        })


    }

    private fun openActivityMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}