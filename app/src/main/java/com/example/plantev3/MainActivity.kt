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


fun changeMood(uvi: Float, temp: Float, moist: Float, myPlant: Plante) {

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
        if (uvi >= myPlant.getUviRange()[0] && uvi <= myPlant.getUviRange()[1]) {
            myPlant.setUviMood(getUviStateConst()[1])
        } else if (uvi <= myPlant.getUviRange()[0]) {
            myPlant.setUviMood(getUviStateConst()[0])
        } else if (uvi >= myPlant.getUviRange()[1]) {
            myPlant.setUviMood(getUviStateConst()[2])
        }
    } else {
        myPlant.setUviMood(getUviStateConst()[1])
    }


    if (temp >= myPlant.getTempRange()[0] && temp <= myPlant.getTempRange()[1]) {
        myPlant.setTempMood(getTempStateConst()[1])
    } else if (temp <= myPlant.getTempRange()[0]) {
        myPlant.setTempMood(getTempStateConst()[0])
    } else if (temp >= myPlant.getTempRange()[1]) {
        myPlant.setTempMood(getTempStateConst()[2])
    }


    if (moist >= myPlant.getMoistRange()[0] && moist <= myPlant.getMoistRange()[1]) {
        myPlant.setMoistMood(getMoistStateConst()[1])
    } else if (moist <= myPlant.getMoistRange()[0]) {
        myPlant.setMoistMood(getMoistStateConst()[0])
    } else if (moist >= myPlant.getMoistRange()[1]) {
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

        val btnHumid:Button = findViewById(R.id.humidityButton)
        val btnTemp:Button = findViewById(R.id.temperatureButton)
        val btnUvi:Button = findViewById(R.id.uviButton)

        moodImage = findViewById(R.id.plantMood)
        moodText = findViewById(R.id.plantMoodText)


        // Write data to app
        val capteur = FirebaseDatabase.getInstance().reference.child("Capteur")
        val temp = capteur.child("Temperature")
        val humid = capteur.child("Moist")
        val uvi = capteur.child("Uvi")

        humid.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Humid Error: ", error.message)

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                btnHumid.text = snapshot.value.toString()
                Log.d("Humid: ",snapshot.value.toString())
            }
        })

        temp.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Temp Error: ", error.message)

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                btnTemp.text = snapshot.value.toString()
                Log.d("Temp: ",snapshot.value.toString())
            }

        })

        uvi.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("Temp Error: ", error.message)

            }
            override fun onDataChange(snapshot: DataSnapshot) {
                btnUvi.text = snapshot.value.toString()
                Log.d("Temp: ",snapshot.value.toString())
            }

        })


    }

    fun changeMood(uvi: Float, temp: Float, moist: Float, myPlant: Plante) {

        val currentTime = LocalDateTime.now(ZoneId.systemDefault())
        val night = LocalDateTime.of(
            currentTime.year,
            currentTime.monthValue,
            currentTime.dayOfMonth,
            20,
            0
        )
        val day =
            LocalDateTime.of(currentTime.year, currentTime.monthValue, currentTime.dayOfMonth, 8, 0)

        if (currentTime.isBefore(night) && currentTime.isAfter(day)) {
            if (uvi >= myPlant.getUviRange()[0] && uvi <= myPlant.getUviRange()[1]) {
                myPlant.setUviMood(getUviStateConst()[1])
            } else if (uvi <= myPlant.getUviRange()[0]) {
                myPlant.setUviMood(getUviStateConst()[0])
            } else if (uvi >= myPlant.getUviRange()[1]) {
                myPlant.setUviMood(getUviStateConst()[2])
            }
        } else {
            myPlant.setUviMood(getUviStateConst()[1])
        }


        if (temp >= myPlant.getTempRange()[0] && temp <= myPlant.getTempRange()[1]) {
            myPlant.setTempMood(getTempStateConst()[1])
        } else if (temp <= myPlant.getTempRange()[0]) {
            myPlant.setTempMood(getTempStateConst()[0])
        } else if (temp >= myPlant.getTempRange()[1]) {
            myPlant.setTempMood(getTempStateConst()[2])
        }


        if (moist >= myPlant.getMoistRange()[0] && moist <= myPlant.getMoistRange()[1]) {
            myPlant.setMoistMood(getMoistStateConst()[1])
        } else if (moist <= myPlant.getMoistRange()[0]) {
            myPlant.setMoistMood(getMoistStateConst()[0])
        } else if (moist >= myPlant.getMoistRange()[1]) {
            myPlant.setMoistMood(getMoistStateConst()[2])
        }

        myPlant.setMood()
    }
}

