package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Osiagniecia : AppCompatActivity() {

    private var btDzien: Button? = null
    private var btRok: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_osiagniecia)

        btDzien = findViewById(R.id.btDzien)

        btDzien?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivitySukces()
            }
        })

        btRok = findViewById(R.id.btRok)

        btRok?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityPorazka()
            }
        })
    }
    private fun openActivitySukces(){
        val intent = Intent(this, OsiagnieciaSukces::class.java)
        startActivity(intent)
    }

    private fun openActivityPorazka(){
        val intent = Intent(this, OsiagnieciaPorazka::class.java)
        startActivity(intent)
    }
}