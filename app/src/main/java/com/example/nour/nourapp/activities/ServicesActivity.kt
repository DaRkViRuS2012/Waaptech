package com.example.nour.nourapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.nour.nourapp.R
import com.example.nour.nourapp.adapters.ServiceAdapter
import com.example.nour.nourapp.data.DataStore
import kotlinx.android.synthetic.main.service_activity.*
import okhttp3.*
import java.io.IOException
import okhttp3.OkHttpClient
import com.example.nour.nourapp.data.ServerAccess;
import com.example.nour.nourapp.data.ServerResult;




/**
 * Created by nour on 7/21/18.
 */



class ServicesActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.service_activity)

        recyclerview_serviceactivity.layoutManager = LinearLayoutManager(this)
        recyclerview_serviceactivity.adapter = ServiceAdapter()


        fetchJSON()

    }


    fun fetchJSON(){
//            println("attimpint to connect")
//
//
//        val url = "https://api.myjson.com/bins/cts62"
//        val request = Request.Builder().url(url).build()
//
//        val client = OkHttpClient()
//
//        client.newCall(request).enqueue(object :Callback{
//
//            override fun onResponse(call: Call?, response: Response?) {
//                val boady = response?.body().toString()
//                println("success")
//                println(boady)
//            }
//
//            override fun onFailure(call: Call?, e: IOException?) {
//                println("Not success")
//            }
//
//        }
//        )
//
        DataStore.getInstance().getServices { result, success ->
          if(result.pairs.containsKey("services")){
                println(result)
          }
        }

    }
}