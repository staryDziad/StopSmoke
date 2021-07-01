package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.Timestamp
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Zdrowie : AppCompatActivity() {

    private var btDodajWynik: Button? = null
    private var edNikotyna: TextView? = null
    private var edCzas: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zdrowie)
        btDodajWynik = findViewById(R.id.btDodajWynik)
        val btBMI = findViewById<ImageButton>(R.id.btBMI)
        edNikotyna = findViewById(R.id.edNikotyna)
        edCzas = findViewById(R.id.edZyskanyCzas)

        FireStoreClass().getUserDetails(this@Zdrowie)




        btBMI?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                val mDialogView = LayoutInflater.from(this@Zdrowie).inflate(R.layout.bmi_popup, null)
                val mBuilder = AlertDialog.Builder(this@Zdrowie)
                    .setView(mDialogView)
                    .setTitle("BMI")

                val mAlertDialog = mBuilder.show()
                val btLicz = mDialogView.findViewById<Button>(R.id.btCalcBMI)
                val btWyjdz = mDialogView.findViewById<Button>(R.id.btWyjdz)
                val waga = mDialogView.findViewById<EditText>(R.id.edNr1)
                val wzrost = mDialogView.findViewById<EditText>(R.id.edNr2)
                val txCokolwiek = mDialogView.findViewById<TextView>(R.id.txCokolwiek)



                btLicz.setOnClickListener{

                    var wartoscWagi = 0.0
                    var wartoscWzrostu = 0.0

                    if (waga.text.toString().isNotEmpty()) {
                        wartoscWagi = waga.text.toString().toDouble()
                    }
                    if (wzrost.text.toString().isNotEmpty()) {
                        wartoscWzrostu = wzrost.text.toString().toDouble()
                    }

                    if (wartoscWagi > 0.0 && wartoscWzrostu > 0.0){
                        val n1 = waga.text.toString().toDouble()
                        val n2 = wzrost.text.toString().toDouble()
                        val wskaznikBMI = obliczBMI(n1, n2)
                        val bmiString = String.format("%.2f", wskaznikBMI)
                        val dopWaga = dopuszczalnaWaga(n2)
                        val status = bmiStatusWartosc(wskaznikBMI)
                        val roznica = roznica(wskaznikBMI, n1, n2)
                        txCokolwiek?.setText("$bmiString \n $status \n $dopWaga \n $roznica ")
                    } else{
                        tost()
                    }

                }

                btWyjdz.setOnClickListener{
                    mAlertDialog.dismiss()
                }
            }
        })


        btDodajWynik?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                openActivityDodajWynik()
            }
        })
    }


    fun showUserInfo(user: User) {
        var today = LocalDateTime.now()
        var ostatniPapieros = user.dataOstatniego.toLocalDateTime()
        var dniBezPalenia = Duration.between(ostatniPapieros, today).toDays()
        var iloscPapierosowDziennie = user.iloscPapierosow

        var nikotyna = dniBezPalenia
        edNikotyna?.text = "Zaoszczędzasz swojemu organizmowi $nikotyna mg nikotyny we krwi"
       /* var tekst = ""
        if(nikotyna < 999){
            tekst = "Zaoszczędzasz swojemu organizmowi $nikotyna mg nikotyny we krwi"
        }else{
            var nik = nikotyna/1000
            tekst = "Zaoszczędzasz swojemu organizmowi $nikotyna g nikotyny we krwi"
        }
        edNikotyna?.setText(tekst)*/

        var czasZaoszczedzony = 28.57*iloscPapierosowDziennie*dniBezPalenia
        var czasFormat = String.format("%.2f", czasZaoszczedzony)
        edCzas?.text = "Zyskujesz" +czasFormat+ " minut życia"

       /* var czasGodz = czasZaoszczedzony/60
        var teks1 = ""
        if(czasZaoszczedzony < 60){
            teks1 = "Zyskujesz $czasZaoszczedzony minut życia"
        }else if(czasGodz > 1 && czasGodz < 999){
            teks1 = "Zyskujesz $czasGodz godzin życia"
        }else{
            var czasDni = czasGodz/24
            teks1 = "Zystkujesz $czasDni dni życia"
        }
        edCzas?.setText(teks1)*/


    }


    fun Timestamp.toLocalDateTime(zone: ZoneId = ZoneId.systemDefault()) = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(seconds * 1000 + nanoseconds / 1000000), zone)

    private fun obliczBMI(waga: Double, wzrost: Double): Double{
        return waga/(wzrost*wzrost)
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

    private fun dopuszczalnaWaga(wzrost: Double): String{
        val dopuszczalnaMasaGorna =
            25.0 * wzrost * wzrost
        val wartoscDopuszczalnaMasaGorna = String.format("%.2f", dopuszczalnaMasaGorna)
        val dopuszczalnaMasaDolna =
            18.5 * wzrost * wzrost
        val wartoscDopuszczalnaMasaDolna = String.format("%.2f", dopuszczalnaMasaDolna)
        val komunikatDopuszczalnaMasa =
            "Twoja masa ciała powinna być w zakresie: $wartoscDopuszczalnaMasaDolna - $wartoscDopuszczalnaMasaGorna [kg]"
        return komunikatDopuszczalnaMasa
    }

    private fun roznica(wskaznik: Double, wartoscWagi: Double, wartoscWzrostu: Double): String{
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
        return komunikatRoznica
    }

    private fun tost(){
        Toast.makeText(
            this,
            "Podaj wartość masy ciała i wzrostu powyżej 0",
            Toast.LENGTH_LONG
        )
            .show()
    }

    private fun openActivityDodajWynik() {
        val intentA = Intent(this, ZdrowieDodajWynik::class.java)
        startActivity(intentA)
    }





}