package com.example.stopsmoke

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //private var ileNiePalisz: TextView? = null
    private var ileZaoszczedziles: TextView? = null

    private var btOchota: Button? = null
    private var btOszczedzaj: Button? = null
    private var btOsiagniecia: Button? = null
    private var btZdrowie: Button? = null
    private var btZerowanie: Button? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //ileNiePalisz = findViewById(R.id.txNiePaliszOd)
        ileZaoszczedziles = findViewById(R.id.txZaoszczedzilesX)

        btOchota = findViewById(R.id.btOchota)
        btOszczedzaj = findViewById(R.id.btOszczedzaj)
        btOsiagniecia = findViewById(R.id.btOsiagniecia)
        btZdrowie = findViewById(R.id.btZdrowie)
        btZerowanie = findViewById(R.id.btZdrowie)

        FireStoreClass().getUserDetails(this)

        btOchota?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityOchota()
            }
        })
        btOszczedzaj?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityOszczedzaj()
            }
        })
        btOsiagniecia?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityOsiagniecia()
            }
        })
        btZdrowie?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityZerowanie()
            }
        })
        btZerowanie?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){
                openActivityZdrowie()
            }
        })

    }

    fun showUserInfo(user: User) {
        ileZaoszczedziles?.text = user.cenaPaczki.toString()
        var cena = user.cenaPaczki
    }

    private fun openActivityOchota(){
        val intent = Intent(this, Ochota_na_papierosa::class.java)
        startActivity(intent)
    }
    private fun openActivityOszczedzaj(){
        val intent = Intent(this, Oszczedzaj::class.java)
        startActivity(intent)
    }
    private fun openActivityOsiagniecia(){
        val intent = Intent(this, Osiagniecia::class.java)
        startActivity(intent)
    }
    private fun openActivityZdrowie(){
        val intent = Intent(this, Zdrowie::class.java)
        startActivity(intent)
    }
    private fun openActivityZerowanie(){
        val intent = Intent(this, Zerowanie::class.java)
        startActivity(intent)
    }

}