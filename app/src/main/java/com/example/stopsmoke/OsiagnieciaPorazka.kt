package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class OsiagnieciaPorazka : AppCompatActivity() {

    private var btWrocOsiagnieciaPorazka: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_osiagniecia_porazka)

        btWrocOsiagnieciaPorazka = findViewById(R.id.btWrocOsiagnieciaPorazka)

        btWrocOsiagnieciaPorazka?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityWrocOsiagnieciaPorazka()
            }
        })
    }

    private fun openActivityWrocOsiagnieciaPorazka(){
        val intent = Intent(this, Osiagniecia::class.java)
        startActivity(intent)
    }
}
