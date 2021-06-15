package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class Logowanie : AppCompatActivity() {

    private var btZaloguj : Button? = null
    private var btZarejestruj: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logowanie)
        btZaloguj = findViewById(R.id.btZaloguj)
        btZarejestruj = findViewById((R.id.btZarejestruj))

        btZaloguj?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityMain()
            }
        })

        btZarejestruj?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityRejestracja()
            }
        })
    }


    private fun openActivityMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun openActivityRejestracja(){
        val intent = Intent(this, Rejestracja::class.java)
        startActivity(intent)
    }

}
