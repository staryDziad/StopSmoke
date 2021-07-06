package com.example.stopsmoke

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.bold
import androidx.core.text.color
import androidx.core.text.italic
import com.google.common.base.Ascii.FF
import com.google.common.io.Files.append
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.NumberFormat
import java.time.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var ileNiePalisz: TextView? = null
    private var ileZaoszczedziles: TextView? = null
    private var powitanie: TextView? = null
    private var txLogOut: TextView? = null

    private var btOchota: Button? = null
    private var btOszczedzaj: Button? = null
    private var btOsiagniecia: Button? = null
    private var btZdrowie: Button? = null
    private var btZerowanie: Button? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ileNiePalisz = findViewById(R.id.txNiePaliszOd)
        ileZaoszczedziles = findViewById(R.id.txZaoszczedzilesX)
        powitanie = findViewById(R.id.txPowitanie)
        txLogOut = findViewById(R.id.txLogOut)

        btOchota = findViewById(R.id.btOchota)
        btOszczedzaj = findViewById(R.id.btOszczedzaj)
        btOsiagniecia = findViewById(R.id.btOsiagniecia)
        btZdrowie = findViewById(R.id.btZdrowie)
        btZerowanie = findViewById(R.id.btZerowanie)

        FireStoreClass().getUserDetails(this)

        btOchota?.setOnClickListener { openActivityOchota() }
        btOszczedzaj?.setOnClickListener { openActivityOszczedzaj() }
        btOsiagniecia?.setOnClickListener { openActivityOsiagniecia() }
        btZdrowie?.setOnClickListener { openActivityZdrowie() }
        btZerowanie?.setOnClickListener { openActivityZerowanie() }

        txLogOut?.setOnClickListener {
            Firebase.auth.signOut()
            finish()

            Toast.makeText(
                this,
                "Wylogowano",
                Toast.LENGTH_SHORT
            )
                .show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    fun showUserInfo(user: User) {
        val today = LocalDateTime.now()
        val ostatniPapieros = user.dataOstatniego.toLocalDateTime()
        val dniBezPalenia = Duration.between(ostatniPapieros, today).toDays()
        val cenaPaczki = user.cenaPaczki
        val zaoszczedziles = cenaPaczki * dniBezPalenia.toDouble()
        val zaoszczedzilesPokaz = String.format("%.2f", zaoszczedziles)
        val uzytkownik = user.name
        //ileNiePalisz?.text = "Dni bez papierosa: \n $dniBezPalenia"
        //ileZaoszczedziles?.text = "Oszczędzasz: \n $zaoszczedzilesPokaz PLN"
        powitanie?.text = "Witaj, $uzytkownik !"
        ileNiePalisz?.text = SpannableStringBuilder()
            .append("Dni bez papierosa: \n")
            .color(Color.rgb(255, 0, 0)) { append(dniBezPalenia.toString()) }
        ileZaoszczedziles?.text = SpannableStringBuilder()
            .append("Oszczędzasz: \n")
            .color(Color.rgb(255, 0, 0)) { append("$zaoszczedzilesPokaz PLN") }
    }

    private fun openActivityOchota() {
        val intent = Intent(this, Ochota_na_papierosa::class.java)
        startActivity(intent)
    }

    private fun openActivityOszczedzaj() {
        val intent = Intent(this, Oszczedzaj::class.java)
        startActivity(intent)
    }

    private fun openActivityOsiagniecia() {
        val intent = Intent(this, Osiagniecia::class.java)
        startActivity(intent)
    }

    private fun openActivityZdrowie() {
        val intent = Intent(this, Zdrowie::class.java)
        startActivity(intent)
    }

    private fun openActivityZerowanie() {
        val intent = Intent(this, Zerowanie::class.java)
        startActivity(intent)
    }

    fun Timestamp.toLocalDateTime(zone: ZoneId = ZoneId.systemDefault()) = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(seconds * 1000 + nanoseconds / 1000000), zone
    )

}