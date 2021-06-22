package com.example.stopsmoke


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class LoginActivity : BaseActivity(), View.OnClickListener {

    private var inputEmail: EditText? = null
    private var inputPassword: EditText? = null
    private var loginButton: Button? = null
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword2)
        loginButton = findViewById(R.id.loginButton)


        loginButton?.setOnClickListener{
            //validateRegisterDetails()
            logInRegisteredUser()

        }

    }


    override fun onClick(view: View?) {
        if(view !=null){
            when (view.id){

                R.id.textView4 ->{
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }




    private fun validateLoginDetails(): Boolean {

        return when{
            TextUtils.isEmpty(inputEmail?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(inputPassword?.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password),true)
                false
            }

            else -> {
                //showErrorSnackBar("Your details are valid",false)
                true
            }
        }


    }

    private fun logInRegisteredUser(){


        if(validateLoginDetails()){
            val email = inputEmail?.text.toString().trim(){ it<= ' '}
            val password = inputPassword?.text.toString().trim(){ it<= ' '}

            //Log-in using FirebaseAuth

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{task ->

                    if(task.isSuccessful){
                        FireStoreClass().getUserDetails(this)
                        showErrorSnackBar("You are logged in successfully.", false)
                        goToMainActivity()
                        //finish()

                    } else{
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }
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

    private val mFireStore = FirebaseFirestore.getInstance()

    fun getUserDetails(activity: Activity): String {
        var rezultat: String = ""
        mFireStore.collection(Constant.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())
                val user = document.toObject(User::class.java)!!

                rezultat = user.cenaPaczki.toString()
            }
        return rezultat
    }

//    open fun readData(): String {
//
//        var cenaPaczki: String = ""
//        val user = FirebaseAuth.getInstance().currentUser;
//        val uid = user?.email.toString()
//
//        database = FirebaseDatabase.getInstance().getReference("users")
//        database.child(uid).get().addOnSuccessListener {
//            cenaPaczki = it.child("cenaPaczki").value.toString()
//
//            val iloscPapierosow = it.child("iloscPapierosow").value.toString()
//
//        }
//        return cenaPaczki
//    }


    open fun goToMainActivity() {

        val user = FirebaseAuth.getInstance().currentUser;
        val uid = user?.email.toString()

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("uID", uid)
        intent.putExtra("cenaPaczki", getUserDetails(this))
        startActivity(intent)


    }


    fun userLoggedInSuccess(user: User){

        Log.i("Email: ", user.email)
        Log.i("name: ", user.name)
        Log.i("liczba: ", user.iloscPapierosow.toString())
        Log.i("cena: ", user.cenaPaczki.toString())
        Log.i("data: ", user.dataOstatniego.toString())


        goToMainActivity()
        finish()
    }

}