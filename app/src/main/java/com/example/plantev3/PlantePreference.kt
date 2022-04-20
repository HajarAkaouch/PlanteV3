package com.example.plantev3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pref-plante")
data class PlantePreference(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    @ColumnInfo(name = "humidity-preference")
    val humidPref: String = "",
    val uviPref: String = "",
    val tempPref: String = "")
