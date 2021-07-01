package com.example.stopsmoke

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.firebase.Timestamp
import com.google.protobuf.Empty
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Oszczedzaj : AppCompatActivity() {

    private var btCele: Button? = null
    private var txCel1: TextView? = null
    private var progr = 0
    private var mDialog : Dialog? = null
    private var btProba: Button? = null
    private var cel1_popup: EditText? = null
    private var cena1_popup: EditText? = null
    private var btOk: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oszczedzaj)
        btCele = findViewById(R.id.btCele)
        txCel1 = findViewById(R.id.txCel1)
        mDialog = Dialog(this)
        btProba = findViewById(R.id.btProba)
        cel1_popup = findViewById(R.id.cel1_popup)
        cena1_popup = findViewById(R.id.cena1_popup)
        btOk = findViewById(R.id.btOk)

        FireStoreClass().getUserDetails(this)

        btProba?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_cel1)
            mDialog!!.show()
            var price = cena1_popup
            var goal = cel1_popup

            //mDialog!!.setCanceledOnTouchOutside(true)
            //mDialog!!.dismiss()
            /*                btOk?.setOnClickListener(object: View.OnClickListener{
                                override fun onClick(v: View){
                                    //mDialog!!.setContentView(R.layout.popup_cel1)
                                    mDialog!!.dismiss()
                                }
                            })*/
        }

        btOk?.setOnClickListener {
            mDialog!!.dismiss()
            //finish()
        }

//        mDialog!!.setOnShowListener {
//            btOk?.setOnClickListener {
//                mDialog!!.dismiss()
//            }
//        }

/*        btOk?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View){

                mDialog!!.dismiss()

            }
        })*/

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
        val cena2 = user.cena2
        val cena3 = user.cena3
        val cel1 = user.cel1
        val cel2 = user.cel2
        val cel3 = user.cel3

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

    fun Timestamp.toLocalDateTime(zone: ZoneId = ZoneId.systemDefault()) = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(seconds * 1000 + nanoseconds / 1000000), zone)
}