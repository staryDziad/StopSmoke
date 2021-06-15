package com.example.stopsmoke


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

class UserInfoActivity : AppCompatActivity() {


    private var backButton: Button? = null
    private var setNewPhoneNumber: Button? = null
    private var emailView: TextView? = null
    private var nameView: TextView? = null
    private var phoneView: TextView? = null
    private var uptadePhone: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        backButton = findViewById(R.id.backButton)
        uptadePhone = findViewById(R.id.editTextNewPhone)
        setNewPhoneNumber = findViewById(R.id.newphoneButton)

        emailView = findViewById(R.id.emailView)
        nameView = findViewById(R.id.NameView)
        phoneView = findViewById(R.id.phoneText)


        FireStoreClass().getUserDetails(this)

        setNewPhoneNumber?.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?){
                updatePhoneNumber()
                finish();
                startActivity(getIntent());
            }
        })


        backButton?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                backToMain()
            }
        })

    }

    private fun backToMain(){
        val user = FirebaseAuth.getInstance().currentUser;
        val uid = user?.email.toString()

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("uID",uid)
        startActivity(intent)
    }

    fun showUserInfo(user: User){
        emailView?.text = user.email
        nameView?.text = user.name


    }

    private fun updatePhoneNumber(){

        val mobilePhone = uptadePhone?.text.toString().toLong()
        print("Mobile phone is: $mobilePhone")
        if(mobilePhone != null) {
            FireStoreClass().updateUserPhoneData(this, mobilePhone)
        }else {
            FireStoreClass().updateUserPhoneData(this, 0)
        }
    }


}