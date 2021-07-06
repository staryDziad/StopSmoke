package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class OszczedzajCele : AppCompatActivity() {

    private var btAkceptujCele: Button? = null
    private var btUsunCel: Button? = null

    private var cel1: EditText? = null
    private var cena1: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oszczedzaj_cele)
        btAkceptujCele = findViewById(R.id.btAkceptujCele)
        btUsunCel = findViewById(R.id.btUsunCel)

        cena1 = findViewById(R.id.edCena_1)
        cel1 = findViewById(R.id.edCel_1)

        btAkceptujCele?.setOnClickListener {
            if (cena1?.text.toString().isNotEmpty() && cel1?.text.toString().isNotEmpty()) {
                updateCena1()
                updateCel1()
                finish()
                openActivityBackOszczedzanie()
            } else {
                tost()
            }
        }

        btUsunCel?.setOnClickListener {
            updateCenaUsun()
            updateCelUsun()
            finish()
            openActivityBackOszczedzanie()
            tostUsun()
        }
    }

    private fun tostUsun() {
        Toast.makeText(
            this,
            "Usunięto dotychczas ustalony cel i jego koszt",
            Toast.LENGTH_LONG
        )
            .show()
    }

    private fun updateCelUsun() {
        val cel = ""

        if (cel != null) {
            FireStoreClass().updateCel1(this, cel)
        } else {
            FireStoreClass().updateCel1(this, "Nie wybrano celu")
        }
    }

    private fun updateCenaUsun() {
        val cena = 0.0

        if (cena != null) {
            FireStoreClass().updateCena1(this, cena)
        } else {
            FireStoreClass().updateCena1(this, 0.0)
        }
    }

    private fun openActivityBackOszczedzanie() {
        val intent = Intent(this, Oszczedzaj::class.java)
        startActivity(intent)
    }

    private fun updateCena1() {
        val cena = cena1?.text.toString().toDouble()

        if (cena != null) {
            FireStoreClass().updateCena1(this, cena)
        } else {
            FireStoreClass().updateCena1(this, 0.0)
        }
    }

    private fun updateCel1() {
        val cel = cel1?.text.toString()

        if (cel != null) {
            FireStoreClass().updateCel1(this, cel)
        } else {
            FireStoreClass().updateCel1(this, "Nie wybrano celu")
        }
    }

    private fun tost() {
        Toast.makeText(
            this,
            "Uzupełnij oba pola",
            Toast.LENGTH_LONG
        )
            .show()
    }

}