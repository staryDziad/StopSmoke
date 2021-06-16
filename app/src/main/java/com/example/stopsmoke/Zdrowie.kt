package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import kotlin.math.pow

class Zdrowie : AppCompatActivity() {

    private var btDodajWynik: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zdrowie)
        btDodajWynik = findViewById(R.id.btDodajWynik)

        val waga = findViewById<EditText>(R.id.edWaga)
        val wzrost = findViewById<EditText>(R.id.edWzrost)
        val btBMI = findViewById<ImageButton>(R.id.btBMI)
        val bmi = findViewById<TextView>(R.id.txBMI)
        val bmiStatus = findViewById<TextView>(R.id.txBmiStatus)
        val bmiAgain = findViewById<TextView>(R.id.btBMIAgain)
        val textWaga = findViewById<TextView>(R.id.textWaga)
        val textWzrost = findViewById<TextView>(R.id.textWzost)


        btBMI.setOnClickListener{
            var wartoscWagi = 0.0
            var wartoscWzrostu = 0.0
            if(waga.text.toString().isNotEmpty()){
                wartoscWagi = waga.text.toString().toDouble()
            }
            if(wzrost.text.toString().isNotEmpty()){
                wartoscWzrostu = wzrost.text.toString().toDouble()
            }
                if(wartoscWagi > 0.0 && wartoscWzrostu > 0.0){
                    val potega = wartoscWzrostu*wartoscWzrostu
                    val b = wartoscWagi/potega
                    val wartoscBMI =String.format("%.2f", b)
                    bmi.text = wartoscBMI
                    bmiStatus.text = bmiStatusWartosc(b)
                    bmi.visibility = VISIBLE
                    bmiStatus.visibility = VISIBLE
                    bmiAgain.visibility = VISIBLE
                    btBMI.visibility = GONE
                    textWaga.visibility = GONE
                    textWzrost.visibility = GONE
                }
                else
                    Toast.makeText(this, "Podaj wartość wagi i wzorstu powyżej 0", Toast.LENGTH_LONG).show()
            }

        bmiAgain.setOnClickListener{
            bmi.visibility = GONE
            bmiStatus.visibility = GONE
            bmiAgain.visibility = GONE
            btBMI.visibility = VISIBLE
            textWaga.visibility = VISIBLE
            textWzrost.visibility = VISIBLE
            waga.text.clear()
            wzrost.text.clear()
            waga.requestFocus()
        }


        btDodajWynik?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                openActivityDodajWynik()
            }
        })
    }


    private fun bmiStatusWartosc(bmi: Double): String{
        lateinit var bmiStatus: String
        if(bmi<18.5)
            bmiStatus = "Niedowaga"
        else if(bmi >= 18.5 && bmi < 25)
            bmiStatus = "Norma"
        else if(bmi >= 25 && bmi < 30)
            bmiStatus = "Nadwaga"
        else if(bmi >= 30)
            bmiStatus = "Otyłość"

        return bmiStatus
    }

    private fun openActivityDodajWynik() {
        val intentA = Intent(this, ZdrowieDodajWynik::class.java)
        startActivity(intentA)
    }
}