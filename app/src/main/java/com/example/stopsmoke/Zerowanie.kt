package com.example.stopsmoke

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class Zerowanie : AppCompatActivity() {

    private val mFireStore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wykresiki) //tu sie zmienia zeby byl dobry layout


        val lineChart = findViewById<LineChart>(R.id.lineChart)


        getUserInfo {user ->

            val dane = user.wykresWagi
            if (dane != null) {
                drawChart(lineChart, dane)
            }
        }

    }


    //RYSOWANIE WYKRESU

    fun drawChart(lineChart: LineChart, daneWykresu: Map<String, Double>){

        val entries = ArrayList<Entry>()

        if (daneWykresu != null) {
            for ((key, value) in daneWykresu!!) {

                val l = LocalDate.parse(key, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                val unix = l.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond
                entries.add(Entry(unix.toFloat(), value.toFloat()))
            }
        }


        val vl = LineDataSet(entries, "Wykres masy ciała")

        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f

        lineChart.xAxis.labelRotationAngle = 0f
        lineChart.data = LineData(vl)

        lineChart.axisRight.isEnabled = false
        //lineChart.xAxis.axisMaximum = j+0.1f

        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

        lineChart.description.text = "Dni"
        lineChart.setNoDataText("Brak danych")


        lineChart.animateX(1800, Easing.EaseInExpo)

        val xLabel  = ArrayList<String>()


        val xaxis: XAxis = lineChart.xAxis

        //xaxis.setValueFormatter(IndexAxisValueFormatter(daneWykresu.keys.toList()))

        xaxis.setValueFormatter(object : ValueFormatter() {
            val pattern = "yyyy-MM-dd"
            private val mFormat = SimpleDateFormat(pattern)


            override fun getFormattedValue(value: Float): String {
                val millis = TimeUnit.HOURS.toMillis(value.toLong())
                return mFormat.format(Date(millis))
            }
        })

        xaxis.granularity=1f

    }


    // POBIERANIE DANYCH - FUNKCJE SKOPIOWANE Z FIRESTORECLASS Z MODYFIKACJA - DODANIE
    // CALLBACKA W GETUSERINFO - OPERACJE ASYNCHRONICZNE


    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun getUserInfo(callback:(User) -> Unit) {

        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                //Log.i(activity.javaClass.simpleName, document.toString())
                val user = document.toObject(User::class.java)!!

                callback.invoke(user)
            }

    }

}