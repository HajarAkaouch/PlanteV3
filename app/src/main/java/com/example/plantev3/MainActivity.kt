package com.example.plantev3

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.annotation.NonNull

import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.plantev3.Constants.getMoodConst
import android.app.AlarmManager

import android.app.PendingIntent
import android.view.View
import java.util.*


class MainActivity : AppCompatActivity() {
    //private var myPlante = getMoodConst()
    private var moodText: TextView? = null
    private var nameText: TextView? = null
    private var moodImage: ImageView? = null

    private var settingBtn: Button? = null

    private lateinit var humidText: TextView
    private lateinit var uviText: TextView
    private lateinit var tempText: TextView

    private var plantName: String? = null
    private var plantTemp: String? = null
    private var selectedHumid: String? = null
    private var selectedUvi: String? = null

    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        settingBtn =  findViewById(R.id.settingBtn)

        moodImage = findViewById(R.id.plantMood)
        moodText = findViewById(R.id.plantMoodText)
        nameText = findViewById(R.id.plantName)

        humidText = findViewById(R.id.humidityText)
        uviText = findViewById(R.id.uviText)
        tempText = findViewById(R.id.temperatureText)

        Constants.maPlante.name = intent.getStringExtra(Constants.maPlante.name)
        selectedHumid = intent. getStringExtra(Constants.maPlante.moistLevel)
        plantTemp = intent.getStringExtra(Constants.maPlante.tempLevel)
        selectedUvi = intent.getStringExtra(Constants.maPlante.uviLevel)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)

        nameText?.text = Constants.maPlante.name

        var humidVal: Float = 0F
        var tempVal :Float = 0F
        var uviVal :Float = 0F



        // Get values from Firebase
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
                humidVal = ((snapshot.value.toString().toFloat()- 1.5f) * (100.0f - 0.0f) / ( 0.65f - 1.5f))
                humidText.text = String.format("%.1f", humidVal ) + "%"
                Log.d("Humid: ", snapshot.value.toString())
                setMoodView(uviVal, tempVal, humidVal)
            }


        })

        temp.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Temp Error: ", error.message)

            }

            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val temlol = snapshot.value.toString()
                tempVal = snapshot.value.toString().toFloat()
                tempText.text = temlol + " \u2103"

                Log.d("Temp: ", snapshot.value.toString())
                setMoodView(uviVal, tempVal, humidVal)
            }

        })

        uvi.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Uvi Error: ", error.message)

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                uviText.text = snapshot.value.toString()
                Log.d("Uvi: ", snapshot.value.toString())
                uviVal = snapshot.value.toString().toFloat()

                setMoodView(uviVal, tempVal, humidVal)
            }


        })


        settingBtn?.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
            // finish() if I want to close the mainActivity
        }

    }

    private fun setMoodView(uvi: Float, temp: Float, moist: Float){
        val monMood: Mood = Constants.maPlante.getMood(uvi, temp, moist)
        moodImage?.setImageResource(monMood.getMyImage()!!)
        moodText?.text = monMood.getMyPhrase()
    }


}

