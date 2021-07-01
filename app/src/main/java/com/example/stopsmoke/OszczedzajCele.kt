package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class OszczedzajCele : AppCompatActivity() {

    private var btAkceptujCele: Button? = null

    private var cena1 : EditText? = null
    private var cena2 : EditText? = null
    private var cena3 : EditText? = null
    private var cel1: EditText? = null
    private var cel2: EditText? = null
    private var cel3: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oszczedzaj_cele)
        btAkceptujCele = findViewById(R.id.btAkceptujCele)

        cena1 = findViewById(R.id.cena1)
        cena2 = findViewById(R.id.cena2)
        cena3 = findViewById(R.id.cena3)
        cel1 = findViewById(R.id.cel1)
        cel2 = findViewById(R.id.cel2)
        cel3 = findViewById(R.id.cel3)


        btAkceptujCele?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){

                /*updateCena1()
                updateCena2()
                updateCena3()
                updateCel1()
                updateCel2()
                updateCel3()*/
                openActivityBackOszczedzanie()

                /* var cel1_1 = cel1?.text.toString()
               var cel2_2 = cel2?.text.toString()
               var cel3_3 = cel3?.text.toString()

               var cena1_1 = cena1?.text.toString().toDouble()  ///////////
               var cena2_2 = cena2?.text.toString().toDouble()
               var cena3_3 = cena3?.text.toString().toDouble()*/
               /* val intent = Intent(this@OszczedzajCele, Oszczedzaj::class.java)

                    intent.putExtra("cel1", cel1_1)
                    intent.putExtra("cel2", cel2_2)
                    intent.putExtra("cel3", cel3_3)
                    intent.putExtra("cena1", cena1_1)
                    intent.putExtra("cena2", cena2_2)
                    intent.putExtra("cena3", cena3_3)

                    startActivity(intent)*/

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
    private fun updateCena2(){
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

    private fun updateCel1(){
        val cel = cel1?.text.toString()

        if(cel != null) {
            FireStoreClass().updateCel1(this, cel)
        }else {
            FireStoreClass().updateCel1(this, "Nie wybrano celu")
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
    }


}