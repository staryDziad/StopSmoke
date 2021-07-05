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

    private var cel1: EditText? = null
    private var cena1 : EditText? = null

    /*private var cena2 : EditText? = null
    private var cena3 : EditText? = null
    private var cel2: EditText? = null
    private var cel3: EditText? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oszczedzaj_cele)
        btAkceptujCele = findViewById(R.id.btAkceptujCele)

        cena1 = findViewById(R.id.edCena_1)
        cel1 = findViewById(R.id.edCel_1)

        /*cena2 = findViewById(R.id.cena2)
        cena3 = findViewById(R.id.cena3)
        cel2 = findViewById(R.id.cel2)
        cel3 = findViewById(R.id.cel3)*/


        btAkceptujCele?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){

                if(cena1?.text.toString().isNotEmpty() && cel1?.text.toString().isNotEmpty()){
                    updateCena1()
                    updateCel1()
                    finish()
                    openActivityBackOszczedzanie()
                }else{
                    tost()
                }



               /* updateCena2()
                updateCena3()
                updateCel2()
                updateCel3()*/

            }
        })

    }
    private fun openActivityBackOszczedzanie(){
        val intent = Intent(this, Oszczedzaj::class.java)
        startActivity(intent)
    }

    private fun updateCena1(){
        val cena = cena1?.text.toString().toDouble()

        if(cena != null) {
            FireStoreClass().updateCena1(this, cena)
        }else {
            FireStoreClass().updateCena1(this, 0.0)
        }
    }

    private fun updateCel1(){
        val cel = cel1?.text.toString()

        if(cel != null) {
            FireStoreClass().updateCel1(this, cel)
        }else {
            FireStoreClass().updateCel1(this, "Nie wybrano celu")
        }
    }

    private fun tost(){
        Toast.makeText(
            this,
            "Uzupełnij oba pola",
            Toast.LENGTH_LONG
        )
            .show()
    }

    /*private fun updateCena2(){
        val cena = cena2?.text.toString().toDouble()

        if(cena != null) {
            FireStoreClass().updateCena2(this, cena)
        }else {
            FireStoreClass().updateCena2(this, 0.0)
        }
    }
    private fun updateCena3(){
        val cena = cena3?.text.toString().toDouble()

        if(cena != null) {
            FireStoreClass().updateCena3(this, cena)
        }else {
            FireStoreClass().updateCena3(this, 0.0)
        }
    }


    private fun updateCel2(){
        val cel = cel2?.text.toString()

        if(cel != null) {
            FireStoreClass().updateCel2(this, cel)
        }else {
            FireStoreClass().updateCel2(this, "Nie wybrano celu")
        }
    }
    private fun updateCel3(){
        val cel = cel3?.text.toString()

        if(cel != null) {
            FireStoreClass().updateCel3(this, cel)
        }else {
            FireStoreClass().updateCel3(this, "Nie wybrano celu")
        }
    }*/


}