package com.example.plantev3

import android.app.Application

class prefPlanteApp:Application() {
    val db by lazy{
        PrefDatabase.getInstance(this)
    }
}