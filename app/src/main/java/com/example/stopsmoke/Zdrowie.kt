package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.Timestamp
import java.time.*

class Zdrowie : AppCompatActivity() {

    private var btKontrolujMase: Button? = null
    private var edNikotyna: TextView? = null
    private var edCzas: TextView? = null
    private var btDodajPop: Button? = null
    private var mapaWagi: MutableMap<String, Double>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zdrowie)
        btKontrolujMase = findViewById(R.id.btKontrolujMase)
        edNikotyna = findViewById(R.id.edNikotyna)
        edCzas = findViewById(R.id.edZyskanyCzas)
        btDodajPop = findViewById(R.id.BtDodajPop)

        val btBMI = findViewById<ImageButton>(R.id.btBMI)

        FireStoreClass().getUserDetails(this@Zdrowie)

        btBMI?.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(this@Zdrowie).inflate(R.layout.bmi_popup, null)
            val mBuilder = AlertDialog.Builder(this@Zdrowie)
                .setView(mDialogView)
                .setTitle("BMI")

            val mAlertDialog = mBuilder.show()
            val btLicz = mDialogView.findViewById<Button>(R.id.btLicz)
            val btWyjdz = mDialogView.findViewById<Button>(R.id.btWyjdz)
            val waga = mDialogView.findViewById<EditText>(R.id.edNr1)
            val wzrost = mDialogView.findViewById<EditText>(R.id.edNr2)
            val txCokolwiek = mDialogView.findViewById<TextView>(R.id.txCokolwiek)

            btLicz.visibility = VISIBLE
            txCokolwiek.visibility = GONE

            btLicz.setOnClickListener {

                var wartoscWagi = 0.0
                var wartoscWzrostu = 0.0

                if (waga.text.toString().isNotEmpty()) {
                    wartoscWagi = waga.text.toString().toDouble()
                }
                if (wzrost.text.toString().isNotEmpty()) {
                    wartoscWzrostu = wzrost.text.toString().toDouble()
                }

                if (wartoscWagi > 0.0 && wartoscWzrostu > 0.0) {
                    val n1 = waga.text.toString().toDouble()
                    val n2 = wzrost.text.toString().toDouble()
                    val wskaznikBMI = obliczBMI(n1, n2)
                    val bmiString = String.format("%.2f", wskaznikBMI)
                    val dopWaga = dopuszczalnaWaga(n2)
                    val status = bmiStatusWartosc(wskaznikBMI)
                    val roznica = roznica(wskaznikBMI, n1, n2)
                    txCokolwiek?.setText("# Wartość BMI: $bmiString \n # $status \n # $dopWaga \n # $roznica ")
                    txCokolwiek.visibility = VISIBLE
                    btLicz.visibility = GONE
                } else {
                    tost()
                }
            }

            btWyjdz.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }


        btDodajPop?.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(this@Zdrowie).inflate(R.layout.waga_popup, null)
            val mBuilder = AlertDialog.Builder(this@Zdrowie)
                .setView(mDialogView)
                .setTitle("Dodaj pomiar")

            val mAlertDialog = mBuilder.show()
            val btOk = mDialogView.findViewById<Button>(R.id.btZatwierdzDoWykresu)
            val dataWykres = mDialogView.findViewById<EditText>(R.id.edDataDoWykresu)
            val waga = mDialogView.findViewById<EditText>(R.id.edWagaDoWykresu)

            val today = LocalDate.now()
            dataWykres?.setText(today.toString())

            btOk.setOnClickListener {
                var wartoscWagi = 0.0
                var data = ""

                if (waga?.text.toString().isNotEmpty()) {
                    wartoscWagi = waga?.text.toString().toDouble()
                }

                if (dataWykres?.text.toString().isNotEmpty()) {
                    data = dataWykres?.text.toString()
                }

                mapaWagi?.set(data, wartoscWagi)

                FireStoreClass().updateWykresWagi(this, mapaWagi?.toMap())

                mAlertDialog.dismiss()
            }

        }

        btKontrolujMase?.setOnClickListener { openActivityWykres() }
    }

    fun showUserInfo(user: User) {
        var today = LocalDateTime.now()
        var ostatniPapieros = user.dataOstatniego.toLocalDateTime()
        var dniBezPalenia = Duration.between(ostatniPapieros, today).toDays()
        var iloscPapierosowDziennie = user.iloscPapierosow

        var nikotyna = dniBezPalenia.toDouble()
        var n = przeliczeniaNikotyna(nikotyna)
        edNikotyna?.text = n

        var czasZaoszczedzony = 28.57 * iloscPapierosowDziennie * dniBezPalenia
        var czas = przeliczeniaCzas(czasZaoszczedzony)
        edCzas?.text = czas


        mapaWagi = user.wykresWagi?.toMutableMap()
    }

    fun Timestamp.toLocalDateTime(zone: ZoneId = ZoneId.systemDefault()) = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(seconds * 1000 + nanoseconds / 1000000), zone
    )

    private fun obliczBMI(waga: Double, wzrost: Double): Double {
        return waga / (wzrost * wzrost)
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

    private fun dopuszczalnaWaga(wzrost: Double): String {
        val dopuszczalnaMasaGorna =
            25.0 * wzrost * wzrost
        val wartoscDopuszczalnaMasaGorna = String.format("%.2f", dopuszczalnaMasaGorna)
        val dopuszczalnaMasaDolna =
            18.5 * wzrost * wzrost
        val wartoscDopuszczalnaMasaDolna = String.format("%.2f", dopuszczalnaMasaDolna)
        val komunikatDopuszczalnaMasa =
            "Twoja masa ciała powinna być w zakresie: $wartoscDopuszczalnaMasaDolna - $wartoscDopuszczalnaMasaGorna kg"
        return komunikatDopuszczalnaMasa
    }

    private fun roznica(wskaznik: Double, wartoscWagi: Double, wartoscWzrostu: Double): String {
        var komunikatRoznica = ""
        if (wskaznik < 18.5) {
            var masaPrawidlowa =
                18.5 * wartoscWzrostu * wartoscWzrostu // dla dolnej granicy normy
            var r = masaPrawidlowa - wartoscWagi
            var rW = String.format("%.2f", r)
            komunikatRoznica = "Do normy musisz przybrać na wadze: $rW kg"
        } else if (wskaznik > 25.0) {
            var masaPrawidlowa =
                25.0 * wartoscWzrostu * wartoscWzrostu // dla górnej granicy normy
            var r = wartoscWagi - masaPrawidlowa
            var rW = String.format("%.2f", r)
            komunikatRoznica = "Do normy musisz zrzucić: $rW kg"
        } else {
            komunikatRoznica = "Tak trzymaj!"
        }
        return komunikatRoznica
    }

    private fun tost() {
        Toast.makeText(
            this,
            "Podaj wartość masy ciała i wzrostu powyżej 0",
            Toast.LENGTH_LONG
        )
            .show()
    }

    private fun przeliczeniaNikotyna(nikotyna: Double): String {
        var tekst = ""
        if (nikotyna < 999) {
            var tekstFormat = String.format("%.2f", nikotyna)
            tekst = "Zaoszczędzasz swemu organizmowi $tekstFormat mg nikotyny we krwi"
        } else {
            var nik = nikotyna / 1000
            var tekstFormat = String.format("%.2f", nik)
            tekst = "Zaoszczędzasz swemu organizmowi $tekstFormat g nikotyny we krwi"
        }
        return tekst
    }

    private fun przeliczeniaCzas(czas: Double): String {
        var tekst1 = ""
        if (czas < 60) {
            var czasFormat = String.format("%.2f", czas)
            tekst1 = "Zyskujesz $czasFormat minut życia"
        } else if (czas >= 60 && czas <= 59940) { //odpowiednik 999 godz
            var czasGodz = czas / 60
            var czasFormat1 = String.format("%.2f", czasGodz)
            tekst1 = "Zyskujesz $czasFormat1 godzin życia"
        } else {
            var czasDni = (czas / 24) / 60
            var czasFormat2 = String.format("%.2f", czasDni)
            tekst1 = "Zyskujesz $czasFormat2 dni życia"
        }
        return tekst1
    }

    private fun openActivityWykres() {
        val intentA = Intent(this, ZdrowieWykres::class.java)
        startActivity(intentA)
    }


}