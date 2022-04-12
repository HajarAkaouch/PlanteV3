package com.example.plantev3

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

var myPlante = Plante()

fun changeMood(uvi: String, temp: String, moist: String, myPlant: Plante) {

    val uviF = uvi.toFloat()
    val tempF = temp.toFloat()
    val moistF = moist.toFloat()

    val currentTime = LocalDateTime.now(ZoneId.systemDefault())
    val night = LocalDateTime.of(
        currentTime.year,
        currentTime.monthValue,
        currentTime.dayOfMonth,
        20,
        0
    )
    val day = LocalDateTime.of(currentTime.year, currentTime.monthValue, currentTime.dayOfMonth, 8, 0)

    if (currentTime.isBefore(night) && currentTime.isAfter(day)) {
        if (uviF >= myPlant.getUviRange()[0] && uviF <= myPlant.getUviRange()[1]) {
            myPlant.setUviMood(getUviStateConst()[1])
        } else if (uviF <= myPlant.getUviRange()[0]) {
            myPlant.setUviMood(getUviStateConst()[0])
        } else if (uviF >= myPlant.getUviRange()[1]) {
            myPlant.setUviMood(getUviStateConst()[2])
        }
    } else {
        myPlant.setUviMood(getUviStateConst()[1])
    }


    if (tempF >= myPlant.getTempRange()[0] && tempF <= myPlant.getTempRange()[1]) {
        myPlant.setTempMood(getTempStateConst()[1])
    } else if (tempF <= myPlant.getTempRange()[0]) {
        myPlant.setTempMood(getTempStateConst()[0])
    } else if (tempF >= myPlant.getTempRange()[1]) {
        myPlant.setTempMood(getTempStateConst()[2])
    }


    if (moistF >= myPlant.getMoistRange()[0] && moistF <= myPlant.getMoistRange()[1]) {
        myPlant.setMoistMood(getMoistStateConst()[1])
    } else if (moistF <= myPlant.getMoistRange()[0]) {
        myPlant.setMoistMood(getMoistStateConst()[0])
    } else if (moistF >= myPlant.getMoistRange()[1]) {
        myPlant.setMoistMood(getMoistStateConst()[2])
    }

    myPlant.setMood()
}

class MainActivity : AppCompatActivity() {
    private var moodText: TextView? = null
    private var moodImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingBtn: Button = findViewById(R.id.settingBtn)

        moodImage = findViewById(R.id.plantMood)
        moodText = findViewById(R.id.plantMoodText)
        val humidText: TextView = findViewById(R.id.humidityText)
        val uviText: TextView  = findViewById(R.id.uviText)
        val tempText: TextView  = findViewById(R.id.temperatureText)



        // Write data to app
        val capteur = FirebaseDatabase.getInstance().reference.child("Capteur")
        val temp = capteur.child("Temperature")
        val humid = capteur.child("Moist")
        val uvi = capteur.child("Uvi")

        val uviString = uviText.toString()
        val tempString = tempText.text.toString()
        val humidString = humidText.text.toString()



        humid.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Humid Error: ", error.message)

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                humidText.text = snapshot.value.toString()
                Log.d("Humid: ", snapshot.value.toString())
            }
        })

        temp.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Temp Error: ", error.message)

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                tempText.text = snapshot.value.toString()
                Log.d("Temp: ", snapshot.value.toString())

            }

        })

        uvi.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Temp Error: ", error.message)

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                uviText.text = snapshot.value.toString()
                Log.d("Temp: ", snapshot.value.toString())

            }

        })


        settingBtn.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
            // finish() if I want to close the mainActivity
        }

    }
}

