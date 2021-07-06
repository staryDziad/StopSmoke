package com.example.stopsmoke

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.firebase.Timestamp
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Osiagniecia : AppCompatActivity() {

    private var imBlackPuchar1: ImageView? = null
    private var imBlackPuchar2: ImageView? = null
    private var imBlackPuchar3: ImageView? = null
    private var imPucharMiesiac: ImageView? = null
    private var imPucharPolRoku: ImageView? = null
    private var imPucharRok: ImageView? = null
    private var imMedalBlack1: ImageView? = null
    private var imMedalBlack2: ImageView? = null
    private var imMedalBlack3: ImageView? = null
    private var imMedalTydzien: ImageView? = null
    private var imMedal3Miesiace: ImageView? = null
    private var imMedal9Miesiecy: ImageView? = null
    private var mDialog: Dialog? = null
    private var imHelp: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_osiagniecia)

        imBlackPuchar1 = findViewById(R.id.imBlackPuchar1)
        imBlackPuchar2 = findViewById<ImageView>(R.id.imBlackPuchar2)
        imBlackPuchar3 = findViewById<ImageView>(R.id.imBlackPuchar3)
        imPucharMiesiac = findViewById<ImageView>(R.id.imPucharMiesiac)
        imPucharPolRoku = findViewById<ImageView>(R.id.imPucharPolRoku)
        imPucharRok = findViewById<ImageView>(R.id.imPucharRok)
        imMedalBlack1 = findViewById(R.id.imMedalBlack1)
        imMedalBlack2 = findViewById(R.id.imMedalBlack2)
        imMedalBlack3 = findViewById(R.id.imMedalBlack3)
        imMedalTydzien = findViewById(R.id.imMedalTydzien)
        imMedal3Miesiace = findViewById(R.id.imMedal3Miesiace)
        imMedal9Miesiecy = findViewById(R.id.imMedal9Miesiecy)
        mDialog = Dialog(this)
        imHelp = findViewById(R.id.imHelp)

        imHelp?.setOnClickListener {
            val mDialogView = LayoutInflater.from(this@Osiagniecia)
                .inflate(R.layout.help_osiagniecia_popup, null)
            val mBuilder = AlertDialog.Builder(this@Osiagniecia)
                .setView(mDialogView)
                .setTitle("Twoje osiągnięcia")

            val mAlertDialog = mBuilder.show()
            val btOk = mDialogView.findViewById<Button>(R.id.btOkHelp)

            btOk.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

        imBlackPuchar1?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_nie_osiagniety_cel)
            mDialog!!.show()
        }
        imBlackPuchar2?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_nie_osiagniety_cel)
            mDialog!!.show()
        }
        imBlackPuchar3?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_nie_osiagniety_cel)
            mDialog!!.show()
        }

        imPucharRok?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_osiagniety_cel)
            mDialog!!.show()
        }
        imPucharPolRoku?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_osiagniety_cel)
            mDialog!!.show()
        }
        imPucharMiesiac?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_osiagniety_cel)
            mDialog!!.show()
        }

        imMedalBlack1?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_nie_osiagniety_cel)
            mDialog!!.show()
        }
        imMedalBlack2?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_nie_osiagniety_cel)
            mDialog!!.show()
        }
        imMedalBlack3?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_nie_osiagniety_cel)
            mDialog!!.show()
        }

        imMedalTydzien?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_osiagniety_cel)
            mDialog!!.show()
        }
        imMedal3Miesiace?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_osiagniety_cel)
            mDialog!!.show()
        }
        imMedal9Miesiecy?.setOnClickListener {
            mDialog!!.setContentView(R.layout.popup_osiagniety_cel)
            mDialog!!.show()
        }

        FireStoreClass().getUserDetails(this)

    }

    fun showUserInfo(user: User) {
        val today = LocalDateTime.now()
        val ostatniPapieros = user.dataOstatniego.toLocalDateTime()
        val dniBezPalenia = Duration.between(ostatniPapieros, today).toDays()

        if (dniBezPalenia in 8..30) {
            imMedalBlack3?.visibility = GONE
            imMedalTydzien?.visibility = VISIBLE
        } else if (dniBezPalenia in 31..90) {
            imMedalBlack3?.visibility = GONE
            imMedalTydzien?.visibility = VISIBLE
            imBlackPuchar3?.visibility = GONE
            imPucharMiesiac?.visibility = VISIBLE
        } else if (dniBezPalenia in 90..179) {
            imMedalBlack3?.visibility = GONE
            imMedalTydzien?.visibility = VISIBLE
            imBlackPuchar3?.visibility = GONE
            imPucharMiesiac?.visibility = VISIBLE
            imMedalBlack2?.visibility = GONE
            imMedal3Miesiace?.visibility = VISIBLE
        } else if (dniBezPalenia in 180..272) {
            imMedalBlack3?.visibility = GONE
            imMedalTydzien?.visibility = VISIBLE
            imBlackPuchar3?.visibility = GONE
            imPucharMiesiac?.visibility = VISIBLE
            imMedalBlack2?.visibility = GONE
            imMedal3Miesiace?.visibility = VISIBLE
            imBlackPuchar2?.visibility = GONE
            imPucharPolRoku?.visibility = VISIBLE
        } else if (dniBezPalenia in 273..363) {
            imMedalBlack3?.visibility = GONE
            imMedalTydzien?.visibility = VISIBLE
            imBlackPuchar3?.visibility = GONE
            imPucharMiesiac?.visibility = VISIBLE
            imMedalBlack2?.visibility = GONE
            imMedal3Miesiace?.visibility = VISIBLE
            imBlackPuchar2?.visibility = GONE
            imPucharPolRoku?.visibility = VISIBLE
            imMedalBlack1?.visibility = GONE
            imMedal9Miesiecy?.visibility = VISIBLE
        } else if (dniBezPalenia >= 364) {
            imMedalBlack3?.visibility = GONE
            imMedalTydzien?.visibility = VISIBLE
            imMedalBlack2?.visibility = GONE
            imMedal3Miesiace?.visibility = VISIBLE
            imMedalBlack1?.visibility = GONE
            imMedal9Miesiecy?.visibility = VISIBLE
            imBlackPuchar3?.visibility = GONE
            imPucharMiesiac?.visibility = VISIBLE
            imBlackPuchar2?.visibility = GONE
            imPucharPolRoku?.visibility = VISIBLE
            imBlackPuchar1?.visibility = GONE
            imPucharRok?.visibility = VISIBLE
        }

    }

    fun Timestamp.toLocalDateTime(zone: ZoneId = ZoneId.systemDefault()) = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(seconds * 1000 + nanoseconds / 1000000), zone
    )
}