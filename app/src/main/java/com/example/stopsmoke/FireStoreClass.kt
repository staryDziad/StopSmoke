package com.example.stopsmoke


import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        //mFireStore.collection("users")
        mFireStore.collection(Constant.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user",
                    e
                )
            }

    }

    fun getCurrentUserID(): String {

        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun getUserDetails(activity: Activity) {
        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())
                val user = document.toObject(User::class.java)!!

                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                    is MainActivity -> {
                        activity.showUserInfo(user)
                    }
                }
            }
    }


    fun updateUserIloscPapierosow(activity: Activity, ilosc: Int) {

        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .update("iloscPapierosow", ilosc)
    }

    fun updateUserCenaPapierosow(activity: Activity, cena: Double) {

        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .update("cenaPaczki", cena)
    }

    fun updateData(activity: Activity, data: com.google.firebase.Timestamp) {

        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .update("dataOstatniego", data)
    }

    fun updateWykresWagi(activity: Activity, mapa: Map<String, Float>){
        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .update("dataOstatniego", mapa)
    }



}