package com.example.stopsmoke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
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

        btZapis?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v : View){
                updateLiczbaPapierosow()
                updateCena()
                //finish()
                goToLogin()
            }
        })
        //val today = Calendar.getInstance()
        //kalendarz.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
        //    today.get(Calendar.DAY_OF_MONTH)
        //) { view, year, month, day ->
        //    val month = month + 1
        //    val msg = "You Selected: $day/$month/$year"

        //}


    }

    private fun openActivityMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun updateLiczbaPapierosow(){
        val liczba = edLiczbaPapierosow?.text.toString().toLong()

        if(liczba != null) {
            FireStoreClass().updateUserPhoneData(this, liczba)
        }else {
            FireStoreClass().updateUserPhoneData(this, 0)
        }
    }

    private fun updateCena(){
        val cena = edLiczbaPapierosow?.text.toString().toLong()

        if(cena != null) {
            FireStoreClass().updateUserPhoneData(this, cena)
        }else {
            FireStoreClass().updateUserPhoneData(this, 0)
        }
    }


    private fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}