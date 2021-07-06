package com.example.stopsmoke

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.proto.MaybeDocument.DocumentTypeCase.valueOf
import com.google.firebase.firestore.proto.Target.TargetTypeCase.valueOf
import com.google.firestore.v1.BatchGetDocumentsResponse.ResultCase.valueOf
import com.google.firestore.v1.TargetChange.TargetChangeType.valueOf
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class Zerowanie : AppCompatActivity() {

    private var btZeruj: Button? = null
    private var imHelp2: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zerowanie)

        btZeruj = findViewById(R.id.btZeruj)
        imHelp2 = findViewById(R.id.imHelp2)

        var tmpStamp: Timestamp? = null
        tmpStamp = Timestamp.now()

        btZeruj?.setOnClickListener {
            updateData(tmpStamp!!)
            finish()
            openActivityMain()
            tostZerowanie()
        }

        imHelp2?.setOnClickListener {
            val mDialogView = LayoutInflater.from(this@Zerowanie)
                .inflate(R.layout.zeruj_popup, null)
            val mBuilder = AlertDialog.Builder(this@Zerowanie)
                .setView(mDialogView)
                .setTitle("Zerowanie osiągnięć")

            val mAlertDialog = mBuilder.show()
            val btZamknij = mDialogView.findViewById<Button>(R.id.btZamknijPop)

            btZamknij.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

    }

    private fun updateData(timestamp: Timestamp) {
        if (timestamp != null) {
            FireStoreClass().updateData(this, timestamp)
        } else {
            FireStoreClass().updateData(this, Timestamp(0, 0))
        }
    }

    private fun openActivityMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun tostZerowanie() {
        Toast.makeText(
            this,
            "Data ostatniego papierosa została wyzerowana",
            Toast.LENGTH_LONG
        )
            .show()
    }

}