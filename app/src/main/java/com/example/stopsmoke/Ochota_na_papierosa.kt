package com.example.stopsmoke

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import pl.droidsonroids.gif.GifImageButton

class Ochota_na_papierosa : AppCompatActivity() {

    private var btGif1: GifImageButton? = null
    private var btGif2: GifImageButton? = null
    private var btGif3: GifImageButton? = null
    private var mDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ochota_na_papierosa)
        btGif1 = findViewById(R.id.btGif1)
        btGif2 = findViewById(R.id.btGif2)
        btGif3 = findViewById(R.id.btGif3)
        mDialog = Dialog(this)

        btGif1?.setOnClickListener {
            mDialog!!.setContentView(R.layout.papieros_gif_1)
            mDialog!!.show()
        }

        btGif2?.setOnClickListener {
            mDialog!!.setContentView(R.layout.papieros_gif_2)
            mDialog!!.show()
        }

        btGif3?.setOnClickListener {
            mDialog!!.setContentView(R.layout.papieros_gif_3)
            mDialog!!.show()
        }
    }
}