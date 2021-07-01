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
        val prawidlowaMasa = findViewById<TextView>(R.id.txPrawidlowaMasa)
        val roznica = findViewById<TextView>(R.id.txRoznica)

        btBMI.setOnClickListener {
            var wartoscWagi = 0.0
            var wartoscWzrostu = 0.0

            if (waga.text.toString().isNotEmpty()) {
                wartoscWagi = waga.text.toString().toDouble()
            }
            if (wzrost.text.toString().isNotEmpty()) {
                wartoscWzrostu = wzrost.text.toString().toDouble()
            }

            if (wartoscWagi > 0.0 && wartoscWzrostu > 0.0) {
                val potega = wartoscWzrostu * wartoscWzrostu
                val wskaznik = wartoscWagi / potega
                val wartoscBMI = String.format("%.2f", wskaznik)

                val dopuszczalnaMasaGorna =
                    25.0 * wartoscWzrostu * wartoscWzrostu
                val wartoscDopuszczalnaMasaGorna = String.format("%.2f", dopuszczalnaMasaGorna)
                val dopuszczalnaMasaDolna =
                    18.5 * wartoscWzrostu * wartoscWzrostu
                val wartoscDopuszczalnaMasaDolna = String.format("%.2f", dopuszczalnaMasaDolna)
                val komunikatDopuszczalnaMasa =
                    "Twoja masa ciała powinna być w zakresie: $wartoscDopuszczalnaMasaDolna - $wartoscDopuszczalnaMasaGorna [kg]"

                var komunikatRoznica = ""
                if (wskaznik < 18.5) {
                    var masaPrawidlowa =
                        18.5 * wartoscWzrostu * wartoscWzrostu // dla dolnej granicy normy
                    var r = masaPrawidlowa - wartoscWagi
                    var rW = String.format("%.2f", r)
                    komunikatRoznica = "Do normy musisz przybrać na wadze: $rW [kg]"
                } else if (wskaznik > 25.0) {
                    var masaPrawidlowa =
                        25.0 * wartoscWzrostu * wartoscWzrostu // dla górnej granicy normy
                    var r = wartoscWagi - masaPrawidlowa
                    var rW = String.format("%.2f", r)
                    komunikatRoznica = "Do normy musisz zrzucić: $rW [kg]"
                } else {
                    komunikatRoznica = "Tak trzymaj!"
                }

                bmi.text = wartoscBMI
                bmiStatus.text = bmiStatusWartosc(wskaznik)
                prawidlowaMasa.text = komunikatDopuszczalnaMasa
                roznica.text = komunikatRoznica
                bmi.visibility = VISIBLE
                bmiStatus.visibility = VISIBLE
                bmiAgain.visibility = VISIBLE
                prawidlowaMasa.visibility = VISIBLE
                roznica.visibility = VISIBLE
                btBMI.visibility = GONE
                textWaga.visibility = GONE
                textWzrost.visibility = GONE
            } else
                Toast.makeText(
                    this,
                    "Podaj wartość masy ciała i wzrostu powyżej 0",
                    Toast.LENGTH_LONG
                )
                    .show()
        }

        bmiAgain.setOnClickListener {
            bmi.visibility = GONE
            bmiStatus.visibility = GONE
            bmiAgain.visibility = GONE
            prawidlowaMasa.visibility = GONE
            roznica.visibility = GONE
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

    private fun bmiStatusWartosc(bmi: Double): String {
        lateinit var bmiStatus: String
        if (bmi < 16.0)
            bmiStatus = "Wygłodzenie"
        else if (bmi >= 16.0 && bmi < 17.0)
            bmiStatus = "Wychudzenie"
        else if (bmi >= 17.0 && bmi < 18.5)
            bmiStatus = "Niedowaga"
        else if (bmi >= 18.5 && bmi < 25.0)
            bmiStatus = "Prawidłowa masa ciała"
        else if (bmi >= 25.0 && bmi < 30.0)
            bmiStatus = "Nadwaga"
        else if (bmi >= 30.0 && bmi < 35.0)
            bmiStatus = "Otyłość I stopnia"
        else if (bmi >= 35.0 && bmi < 40.0)
            bmiStatus = "Otyłość II stopnia (duża)"
        else if (bmi >= 40.0)
            bmiStatus = "Otyłość III stopnia (chorobliwa)"
        return bmiStatus
    }

    private fun openActivityDodajWynik() {
        val intentA = Intent(this, ZdrowieDodajWynik::class.java)
        startActivity(intentA)
    }
}