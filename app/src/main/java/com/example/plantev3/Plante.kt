package com.example.plantev3

import com.example.plantev3.Constants.getMoistStateConst
import com.example.plantev3.Constants.getMoodConst
import com.example.plantev3.Constants.getTempStateConst
import com.example.plantev3.Constants.getUviStateConst

class Plante constructor(name: String, moistLevel: String, tempLevel: String, uviLevel: String,arrose: String) {

    var name: String? = null
    var moistLevel: String? = null
    var tempLevel: String? = null
    var uviLevel: String? = null

    var tempState: String? = null
    var moistState: String? = null
    var uviState: String? = null

    var mood: Mood? = null

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

    fun getMoistRange():ArrayList<Float>{
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

    fun getTempRange():ArrayList<Float>{
        val tempR = ArrayList<Float>()
        val min: Float? = tempLevel?.toFloat()?.minus(5.0F)
        val max: Float? = tempLevel?.toFloat()?.plus(5.0F)

        if (min != null && max != null) {
            tempR.add(0, min)
            tempR.add(0, max)
        }

        return tempR
    }

    fun getUviRange():ArrayList<Float>{
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
            uviR.add(0, 12.0F)
        }

        return uviR
    }

    fun setTempMood(state: String){
        tempState = state
    }
    fun setMoistMood(state: String){
        moistState = state
    }
    fun setUviMood(state:String){
        uviState = state
    }

    fun setMood(){

        val good:Boolean = tempState == getTempStateConst()[1] && moistState == getMoistStateConst()[1] && uviState == getUviStateConst()[1]

        val medium:Boolean = (tempState == getTempStateConst()[1] && moistState == getMoistStateConst()[1] && (uviState == getUviStateConst()[0] || uviState == getUviStateConst()[2])) ||
                (tempState == getTempStateConst()[1] && (moistState == getMoistStateConst()[0] || moistState == getMoistStateConst()[2]) && uviState == getUviStateConst()[1]) ||
                ((tempState == getTempStateConst()[0] || tempState == getTempStateConst()[2]) && moistState == getMoistStateConst()[1] && uviState == getUviStateConst()[1])

        val bad:Boolean = (tempState == getTempStateConst()[1] && (moistState == getMoistStateConst()[0] || moistState == getMoistStateConst()[2]) && (uviState == getUviStateConst()[0] || uviState == getUviStateConst()[2]))||
                ((tempState == getTempStateConst()[0] || tempState == getTempStateConst()[2]) && moistState == getMoistStateConst()[1] && (uviState == getUviStateConst()[0] || uviState == getUviStateConst()[2]))||
                ((tempState == getTempStateConst()[0] || tempState == getTempStateConst()[2]) && (moistState == getMoistStateConst()[0] || moistState == getMoistStateConst()[2]) && uviState == getUviStateConst()[1])

        if(good){
            mood = getMoodConst()[0]
        }
        if(medium){
            mood = getMoodConst()[1]
        }
        if(bad){
            mood = getMoodConst()[2]
        }
    }
}