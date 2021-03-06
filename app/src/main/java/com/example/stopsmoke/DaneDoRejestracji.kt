package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class DaneDoRejestracji : AppCompatActivity() {

    private var btZapis: Button? = null

    private var edLiczbaPapierosow: EditText? = null
    private var edCenaPapierosow: EditText? = null
    private var kalendarz: DatePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dane_do_rejestracji)

        btZapis = findViewById(R.id.btZapis)

        edLiczbaPapierosow = findViewById(R.id.edLiczbaPapierosow)
        edCenaPapierosow = findViewById(R.id.edCenaPapierosow)
        kalendarz = findViewById(R.id.kalendarz)

        var tmpStamp: Timestamp? = null

        val today = Calendar.getInstance()
        kalendarz?.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->

            val c = Calendar.getInstance()
            c.set(year, month, day)
            /* if(c <= today){
                 tmpStamp = Timestamp(c.time)
             }else{
                 tostZlyDzien()
             }*/
            tmpStamp = Timestamp(c.time)

        }

        btZapis?.setOnClickListener {

            val today = LocalDateTime.now()
            val tmpPodany = tmpStamp?.toLocalDateTime()
            val roznica = Duration.between(tmpPodany, today).toDays()

            if (edCenaPapierosow?.text.toString()
                    .isNotEmpty() && edLiczbaPapierosow?.text.toString()
                    .isNotEmpty() && tmpStamp != null && roznica >= 0
            ) {
                updateLiczbaPapierosow()
                updateCena()
                updateData(tmpStamp!!)
                finish()
                goToLogin()
                tostSukces()
            } else {
                tost()
            }
        }
    }

    private fun updateData(timestamp: Timestamp) {
        if (timestamp != null) {
            FireStoreClass().updateData(this, timestamp)
        } else {
            FireStoreClass().updateData(this, Timestamp(0, 0))
        }
    }

    private fun updateLiczbaPapierosow() {
        val liczba = edLiczbaPapierosow?.text.toString().toInt()

        if (liczba != null) {
            FireStoreClass().updateUserIloscPapierosow(this, liczba)
        } else {
            FireStoreClass().updateUserIloscPapierosow(this, 0)
        }
    }

    private fun updateCena() {
        val cena = edLiczbaPapierosow?.text.toString().toDouble()

        if (cena != null) {
            FireStoreClass().updateUserCenaPapierosow(this, cena)
        } else {
            FireStoreClass().updateUserCenaPapierosow(this, 0.0)
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun tost() {
        Toast.makeText(
            this,
            "Uzupe??nij poprawnie wszystkie pola",
            Toast.LENGTH_LONG
        )
            .show()
    }

    fun tostSukces() {
        Toast.makeText(
            this,
            resources.getString(R.string.register_success),
            Toast.LENGTH_LONG
        ).show()
    }

    fun Timestamp.toLocalDateTime(zone: ZoneId = ZoneId.systemDefault()) = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(seconds * 1000 + nanoseconds / 1000000), zone
    )

}