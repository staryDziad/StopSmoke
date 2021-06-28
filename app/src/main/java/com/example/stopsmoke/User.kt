package com.example.stopsmoke

import com.google.firebase.Timestamp


class User (val id: String = "",
            val name: String = "",
            val email: String = "",
            val cenaPaczki: Double = 0.0,
            val iloscPapierosow: Int = 0,
            val dataOstatniego: Timestamp = Timestamp(0,0),
            val wykresWagi: Map<String, Float>? = null
)
