package com.example.stopsmoke

import com.google.firebase.Timestamp

class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val cenaPaczki: Double = 0.0,
    val iloscPapierosow: Int = 0,
    val dataOstatniego: Timestamp = Timestamp.now(),
    val wykresWagi: Map<String, Double>? = emptyMap<String, Double>(),
    val cel1: String = "",
    val cena1: Double = 0.0
)
