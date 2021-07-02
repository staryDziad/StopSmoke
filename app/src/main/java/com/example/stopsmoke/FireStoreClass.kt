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

                //activity.userRegistrationSuccess()
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
                    is Osiagniecia -> {
                        activity.showUserInfo(user)
                    }
                    is Oszczedzaj -> {
                        activity.showUserInfo(user)
                    }
                    is Zdrowie -> {
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

    fun updateCel1(activity: Activity, cel1: String) {

        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .update("cel1", cel1)
    }
    fun updateCel2(activity: Activity, cel2: String) {

        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .update("cel2", cel2)
    }
    fun updateCel3(activity: Activity, cel3: String) {

        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .update("cel3", cel3)
    }
    fun updateCena1(activity: Activity, cena1: Double) {

        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .update("cena1", cena1)
    }
    fun updateCena2(activity: Activity, cena2: Double) {

        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .update("cena2", cena2)
    }
    fun updateCena3(activity: Activity, cena3: Double) {

        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .update("cena3", cena3)
    }



}