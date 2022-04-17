package com.example.plantev3

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.os.StrictMode
import android.util.Log
import android.widget.ImageView
import com.example.plantev3.Constants.getMoistStateConst
import com.example.plantev3.Constants.getTempStateConst
import com.example.plantev3.Constants.getUviStateConst
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener
import java.time.ZoneId



class MainActivity : AppCompatActivity() {
    private var myPlante = Plante()
    private var moodText: TextView? = null
    private var moodImage: ImageView? = null

    private var settingBtn: Button? = null

    private lateinit var humidText: TextView
    private lateinit var uviText: TextView
    private lateinit var tempText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingBtn =  findViewById(R.id.settingBtn)

        moodImage = findViewById(R.id.plantMood)
        moodText = findViewById(R.id.plantMoodText)

        humidText = findViewById(R.id.humidityText)
        uviText = findViewById(R.id.uviText)
        tempText = findViewById(R.id.temperatureText)


        // Write data to app
        val capteur = FirebaseDatabase.getInstance().reference.child("Capteur")
        val temp = capteur.child("Temperature")
        val humid = capteur.child("Moist")
        val uvi = capteur.child("Uvi")

        capteur.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Log.d("Humid Error: ", error.message)

            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val humidlol = snapshot.value.toString()
                humidText.text = String.format("%.1f", (humidlol.toFloat() - 1.5) * (100.0 - 0.0) / ( 0.65 - 1.5)) + "%"
                Log.d("Humid: ", snapshot.value.toString())
            }
        })



        settingBtn?.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
            // finish() if I want to close the mainActivity
        }

    }

    private fun getData(){
        val capteur = FirebaseDatabase.getInstance().reference.child("Capteur")
        val temp = capteur.child("Temperature")
        val humid = capteur.child("Moist")
        val uvi = capteur.child("Uvi")

        humid.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Humid Error: ", error.message)

            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val humidlol = snapshot.value.toString()
                humidText.text = String.format("%.1f", (humidlol.toFloat() - 1.5) * (100.0 - 0.0) / ( 0.65 - 1.5)) + "%"
                Log.d("Humid: ", snapshot.value.toString())
            }


        })

        temp.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Temp Error: ", error.message)

            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val temlol = snapshot.value.toString()
                tempText.text = temlol + " \u2103"

                Log.d("Temp: ", snapshot.value.toString())

            }

        })

        uvi.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Uvi Error: ", error.message)

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                uviText.text = snapshot.value.toString()
                Log.d("Uvi: ", snapshot.value.toString())


            }

        })
    }
}

