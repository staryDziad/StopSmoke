package com.example.stopsmoke

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.Timestamp
import org.w3c.dom.Text
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Oszczedzaj : AppCompatActivity() {

    private var btCele: Button? = null
    private var txCel1: TextView? = null
    private var progr = 0
    private var btZmienCel: Button? = null
    private var imHelp3: ImageView? = null
    private var txPowrot: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oszczedzaj)
        btCele = findViewById(R.id.btCele)
        txCel1 = findViewById(R.id.txCel1)
        btZmienCel = findViewById(R.id.btZmienCel)
        imHelp3 = findViewById(R.id.imHelp3)
        txPowrot = findViewById(R.id.txPowrot)

        FireStoreClass().getUserDetails(this)

        imHelp3?.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(this@Oszczedzaj).inflate(R.layout.cel1_popup, null)
            val mBuilder = AlertDialog.Builder(this@Oszczedzaj)
                .setView(mDialogView)
                .setTitle("Oszczędzaj!")

            val mAlertDialog = mBuilder.show()
            val btOk = mAlertDialog.findViewById<Button>(R.id.btZamknijOkno)
            btOk?.setOnClickListener { mAlertDialog.dismiss() }
        }

        btCele?.setOnClickListener { openActivityCele() }
        btZmienCel?.setOnClickListener { openActivityCele() }
        txPowrot?.setOnClickListener { openActivityMain() }
    }

    @SuppressLint("SetTextI18n")
    fun showUserInfo(user: User) {
        val today = LocalDateTime.now()
        val ostatniPapieros = user.dataOstatniego.toLocalDateTime()
        val dniBezPalenia = Duration.between(ostatniPapieros, today).toDays()
        val cenaPaczki = user.cenaPaczki
        val zaoszczedziles = cenaPaczki * dniBezPalenia.toDouble()

        val cena1 = user.cena1
        val cel1 = user.cel1

        var x = (zaoszczedziles / cena1) * 100
        var x1 = x.toInt()

        if (cena1 == 0.0) {
            progr = 0
            txCel1?.text = "Nie podano celu"

        } else if (x1 in 0..99) {
            progr = x1
            txCel1?.text = cel1
            btCele?.visibility = GONE
            btZmienCel?.visibility = VISIBLE
        } else {
            progr = 100
            txCel1?.text = "Cel: $cel1 \n osiągnięty!"
            btCele?.visibility = GONE
            btZmienCel?.visibility = VISIBLE
        }
        updateProgressBar()
    }

    private fun openActivityCele() {
        val intent = Intent(this, OszczedzajCele::class.java)
        startActivity(intent)
    }

    private fun openActivityMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun updateProgressBar() {
        var progress_bar = findViewById<ProgressBar>(R.id.progress_bar)
        var text_view_progress = findViewById<TextView>(R.id.text_view_progress)
        progress_bar.progress = progr
        text_view_progress.text = "$progr%"
    }

    fun Timestamp.toLocalDateTime(zone: ZoneId = ZoneId.systemDefault()) = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(seconds * 1000 + nanoseconds / 1000000), zone
    )
}