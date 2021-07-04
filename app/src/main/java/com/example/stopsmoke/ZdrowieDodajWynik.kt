package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.time.LocalDate

class ZdrowieDodajWynik : AppCompatActivity() {


    private var btnPotwierdzWynik: Button? = null
    private var txtWaga: EditText? = null
    private var txtData: EditText? = null
    private var mapaWagi: MutableMap<String, Double>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zdrowie_dodaj_wynik)

        btnPotwierdzWynik = findViewById(R.id.btPotwierdzWynik)
        txtWaga = findViewById(R.id.txDodajMasa)
        txtData = findViewById(R.id.txDodajData)

        FireStoreClass().getUserDetails(this)

        val today = LocalDate.now()

        txtData?.setText(today.toString())


    }


    fun btnPotwierdzWynikClick(v: View?) {

        var wartoscWagi = 0.0
        var data = ""

        if (txtWaga?.text.toString().isNotEmpty()) {
            wartoscWagi = txtWaga?.text.toString().toDouble()
        }

        if (txtData?.text.toString().isNotEmpty()) {
            data = txtData?.text.toString()
        }

        println("---------------------------TWOJA STARA DEBUGGING-------------------------------------")
        println(mapaWagi?.keys)


        mapaWagi?.set(data, wartoscWagi)

        println(mapaWagi?.keys)


        FireStoreClass().updateWykresWagi(this, mapaWagi?.toMap())

        val intent = Intent(this, Zdrowie::class.java)
        startActivity(intent)
        finish()
    }


    fun showUserInfo(user: User){
        mapaWagi = user.wykresWagi?.toMutableMap()
    }



}