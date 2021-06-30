package com.example.stopsmoke

import com.google.firebase.Timestamp


class User (val id: String = "",
            val name: String = "",
            val email: String = "",
            val cenaPaczki: Double = 0.0,
            val iloscPapierosow: Int = 0,
            val dataOstatniego: Timestamp = Timestamp(0,0),
            val wykresWagi: Map<String, Float>? = null,
            val cel1: String = "",
            val cena1: Double = 0.0,
            val cel2: String = "",
            val cena2: Double = 0.0,
            val cel3: String = "",
            val cena3: Double = 0.0
)
