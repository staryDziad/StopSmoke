package com.example.stopsmoke

import android.annotation.SuppressLint
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

class Oszczedzaj : AppCompatActivity() {

    private var btCele: Button? = null
    private var txCel1: TextView? = null
    private var progr = 0

    private var btProba: Button? = null
    private var cel1_popup: EditText? = null
    private var cena1_popup: EditText? = null
    private var btOk: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oszczedzaj)
        btCele = findViewById(R.id.btCele)
        txCel1 = findViewById(R.id.txCel1)
        btProba = findViewById(R.id.btProba)
        cel1_popup = findViewById(R.id.cel1_popup)
        //cena1_popup = findViewById(R.id.cel1_popup)
        btOk = findViewById(R.id.btOk)

        FireStoreClass().getUserDetails(this)

        btProba?.setOnClickListener {
            val mDialogView =
                LayoutInflater.from(this@Oszczedzaj).inflate(R.layout.cel1_popup, null)
            val mBuilder = AlertDialog.Builder(this@Oszczedzaj)
                .setView(mDialogView)
                .setTitle("Cel 1")

            val mAlertDialog = mBuilder.show()
            val btZatwierdz = mDialogView.findViewById<Button>(R.id.btDodajCel1)
            val cel1 = mDialogView.findViewById<EditText>(R.id.edCel1Nazwa)
            val cena1 = mDialogView.findViewById<EditText>(R.id.edCel1Cena)

            val btNiewazna = mDialogView.findViewById<Button>(R.id.btNiewazne)
            FireStoreClass().getUserDetails(this@Oszczedzaj)

            btZatwierdz.setOnClickListener {
                updateCel1(cel1)
                updateCena1(cena1)
                mAlertDialog.dismiss()
            }

            btNiewazna.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        btCele?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityCele()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun showUserInfo(user: User) {
        val today = LocalDateTime.now()
        val ostatniPapieros = user.dataOstatniego.toLocalDateTime()
        val dniBezPalenia = Duration.between(ostatniPapieros, today).toDays()
        val cenaPaczki = user.cenaPaczki
        val zaoszczedziles = cenaPaczki*dniBezPalenia.toDouble()

        val cena1 = user.cena1
        val cel1 = user.cel1

        var x = (zaoszczedziles/cena1)*100
        var x1 = x.toInt()

        if(cena1 == 0.0){
            progr = 0
            txCel1?.text = "Nie podano celu"

        }else if(x1 in 1..99){
            progr = x1
            txCel1?.text = cel1
        }else{
            progr = 100
            txCel1?.text = "$cel1 osiągnięty"
        }
        updateProgressBar()
    }

    private fun openActivityCele(){
        val intent = Intent(this, OszczedzajCele::class.java)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun updateProgressBar() {
        var progress_bar = findViewById<ProgressBar>(R.id.progress_bar)
        var text_view_progress = findViewById<TextView>(R.id.text_view_progress)
        progress_bar.progress = progr
        text_view_progress.text = "$progr%"
    }

    private fun updateCena1(cena1: EditText) {
        if(cena1.text.toString().isNotEmpty()){
            val cena = cena1.text.toString().toDouble()

            if(cena != null) {
                FireStoreClass().updateCena1(this, cena)
            }else {
                FireStoreClass().updateCena1(this, 0.0)
            }
        }
    }

    private fun updateCel1(cel1: EditText) {
        if(cel1.text.toString().isNotEmpty()){
            val cel = cel1.text.toString()

            if(cel != null) {
                FireStoreClass().updateCel1(this, cel)
            }else {
                FireStoreClass().updateCel1(this, "Nie wybrano celu")
            }
        }

    }

    fun Timestamp.toLocalDateTime(zone: ZoneId = ZoneId.systemDefault()) = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(seconds * 1000 + nanoseconds / 1000000), zone)
}