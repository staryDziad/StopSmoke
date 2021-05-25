package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class OsiagnieciaSukces : AppCompatActivity() {

    private var btWrocOsiagnieciaSukces: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_osiagniecia_sukces)

        btWrocOsiagnieciaSukces = findViewById(R.id.btWrocOsiagnieciaSukces)

        btWrocOsiagnieciaSukces?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityWrocOsiagnieciaSukces()
            }
        })
    }

    private fun openActivityWrocOsiagnieciaSukces(){
        val intent = Intent(this, Osiagniecia::class.java)
        startActivity(intent)
    }
}