package com.example.plantev3

class Mood(id: String = "",image: Int = 0 , phrase: String = "") {

    var id: String? = null
    var image: Int? = null
    var phrase: String? = null

    init {
        this.id = id
        this.image = image
        this.phrase = phrase
    }
    fun getMyImage(): Int? {
        return image
    }

    fun getMyPhrase(): String? {
        return phrase
    }
}