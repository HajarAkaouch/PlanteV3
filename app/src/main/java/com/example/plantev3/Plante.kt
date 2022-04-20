package com.example.plantev3

import com.example.plantev3.Constants.getMoistStateConst
import com.example.plantev3.Constants.getMoodConst
import com.example.plantev3.Constants.getTempStateConst
import com.example.plantev3.Constants.getUviStateConst
import java.time.LocalDateTime
import java.time.ZoneId

class Plante constructor(name: String = "Ma plante", moistLevel: String = "Humide", tempLevel: String = "25", uviLevel: String = "Ombre") {

    var name: String? = null
    var moistLevel: String? = null
    var tempLevel: String? = null
    var uviLevel: String? = null

    var tempState: String? = null
    var moistState: String? = null
    var uviState: String? = null

    private var mood = Mood()

    init {
        this.moistLevel = moistLevel
        this.name = name
        this.tempLevel = tempLevel
        this.uviLevel = uviLevel

        var tempState = ""
        var moistState= ""
        var uviState = ""

        var mood = Mood()


    }

    private fun getMoistRange():ArrayList<Float>{
        val moistR = ArrayList<Float>()

        if(moistLevel == "Sec"){
            moistR.add(0, 0.0F)
            moistR.add(1, 30.0F)
        }

        if(moistLevel == "Humide"){
            moistR.add(0, 30.01F)
            moistR.add(1, 80.0F)
        }

        if(moistLevel == "Très Humide"){
            moistR.add(0, 80.0F)
            moistR.add(1, 100.0F)
        }

        return moistR
    }

    private fun getTempRange():ArrayList<Float>{
        val tempR = ArrayList<Float>()
        val min: Float? = tempLevel?.toFloat()?.minus(5.0F)
        val max: Float? = tempLevel?.toFloat()?.plus(5.0F)

        if (min != null && max != null) {
            tempR.add(0, min)
            tempR.add(1, max)
        }

        return tempR
    }

    private fun getUviRange():ArrayList<Float>{
        val uviR = ArrayList<Float>()

        if(uviLevel == "Ombre"){
            uviR.add(0, 0.0F)
            uviR.add(1, 2.0F)
        }

        if(moistLevel == "Lumineux"){
            uviR.add(0, 2.0F)
            uviR.add(1, 5F)
        }

        if(uviLevel == "Très lumineux"){
            uviR.add(0, 5.0F)
            uviR.add(1, 10.0F)
        }

        if(uviLevel == "Peu importe"){
            uviR.add(0, 0.0F)
            uviR.add(1, 12.0F)
        }

        return uviR
    }

    private fun setTempMood(state: String){
        tempState = state
    }
    private fun setMoistMood(state: String){
        moistState = state
    }
    private fun setUviMood(state:String){
        uviState = state
    }

    fun getMood(uvi: Float, temp: Float, moist: Float): Mood {

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
            if (uvi >= getUviRange()[0] && uvi <= getUviRange()[1]) {
                setUviMood(getUviStateConst()[1])
            } else if (uvi <= getUviRange()[0]) {
                setUviMood(getUviStateConst()[0])
            } else if (uvi >= getUviRange()[1]) {
                setUviMood(getUviStateConst()[2])
            }
        } else {
            setUviMood(getUviStateConst()[1])
        }


        if (temp >= getTempRange()[0] && temp <= getTempRange()[1]) {
            setTempMood(getTempStateConst()[1])
        } else if (temp <= getTempRange()[0]) {
            setTempMood(getTempStateConst()[0])
        } else if (temp >= getTempRange()[1]) {
            setTempMood(getTempStateConst()[2])
        }


        if (moist >= getMoistRange()[0] && moist <= getMoistRange()[1]) {
            setMoistMood(getMoistStateConst()[1])
        } else if (moist <= getMoistRange()[0]) {
            setMoistMood(getMoistStateConst()[0])
        } else if (moist >= getMoistRange()[1]) {
            setMoistMood(getMoistStateConst()[2])
        }

        val good:Boolean = tempState == getTempStateConst()[1] && moistState == getMoistStateConst()[1] && uviState == getUviStateConst()[1]

        val medium:Boolean = (tempState == getTempStateConst()[1] && moistState == getMoistStateConst()[1] && (uviState == getUviStateConst()[0] || uviState == getUviStateConst()[2])) ||
                (tempState == getTempStateConst()[1] && (moistState == getMoistStateConst()[0] || moistState == getMoistStateConst()[2]) && uviState == getUviStateConst()[1]) ||
                ((tempState == getTempStateConst()[0] || tempState == getTempStateConst()[2]) && moistState == getMoistStateConst()[1] && uviState == getUviStateConst()[1])

        val bad:Boolean = (tempState == getTempStateConst()[1] && (moistState == getMoistStateConst()[0] || moistState == getMoistStateConst()[2]) && (uviState == getUviStateConst()[0] || uviState == getUviStateConst()[2]))||
                ((tempState == getTempStateConst()[0] || tempState == getTempStateConst()[2]) && moistState == getMoistStateConst()[1] && (uviState == getUviStateConst()[0] || uviState == getUviStateConst()[2]))||
                ((tempState == getTempStateConst()[0] || tempState == getTempStateConst()[2]) && (moistState == getMoistStateConst()[0] || moistState == getMoistStateConst()[2]) && uviState == getUviStateConst()[1])

        val moodList: ArrayList<Mood> = getMoodConst()

        when {
            good -> {
                mood = moodList[0]
            }
            medium -> {
                mood = moodList[1]
            }
            bad -> {
                mood = moodList[2]
            }
        }

        return mood
    }
}