package com.example.plantev3

object Constants {
    fun getMoodConst():ArrayList<Mood>{
        val moodList = ArrayList<Mood>()
        val moodHappy = Mood(
            1,
            R.drawable.happy,
            "Je suis heureuse!"
        )
        moodList.add(moodHappy)
        val moodMedium = Mood(
            2,
            R.drawable.medium,
            "Je me sens pas trop bien..."
        )
        moodList.add(moodMedium)
        val moodSad = Mood(
            3,
            R.drawable.sad,
            "Je suis mourru..."
        )
        moodList.add(moodSad)
        return moodList

    }

    fun getTempStateConst():ArrayList<String>{
        val stateList = ArrayList<String>()

        stateList.add("J'ai froid...brrr")
        stateList.add("Je suis bien!")
        stateList.add("J'ai chaud!! Je fond")

        return stateList
    }

    fun getMoistStateConst():ArrayList<String>{
        val stateList = ArrayList<String>()

        stateList.add("J'ai soif...")
        stateList.add("Je suis hydraté UwU")
        stateList.add("Je me noie!!")

        return stateList
    }

    fun getUviStateConst():ArrayList<String>{
        val stateList = ArrayList<String>()

        stateList.add("J'ai besoin de soleil")
        stateList.add("aaaaah comme on est bien")
        stateList.add("AAAH je brule")

        return stateList
    }

}