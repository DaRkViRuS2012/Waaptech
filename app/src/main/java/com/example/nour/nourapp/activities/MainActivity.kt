package com.example.nour.nourapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.nour.nourapp.R
import kotlinx.android.synthetic.main.main_layout.*

/**
 * Created by nour on 7/21/18.
 */

public class MainActivity:AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)


    ourservice_button_main.setOnClickListener {
        onOurServiceClick()
    }



    }



    fun onOurServiceClick(){

        val intent = Intent(this,ServicesActivity::class.java)
        startActivity(intent)
    }

}
