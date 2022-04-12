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

    fun getUviStateConst():ArrayList<String> {
        val stateList = ArrayList<String>()

        stateList.add("J'ai besoin de soleil")
        stateList.add("aaaaah comme on est bien")
        stateList.add("AAAH je brule")

        return stateList
    }

    fun getFunFact():ArrayList<String>{
        val factList = ArrayList<String>()
        factList.add("Les plantes sont sensibles au changement. Ils sont lents à s'adapter à de nouveaux environnements, donc s'ils vont bien, laissez les à leur place!")
        factList.add("Lorsque les racines d'une plante poussent hors de son trou de drainage, ou qu'elle déborde sur le dessus, il est temps de la planter dans un plus gros pot.")
        factList.add("La poussière s'accumule sur les feuilles, alors lavez-les avec une douce douche d'eau à température ambiante ou époussetez-les avec une brosse si les plantes ont des feuilles velues")
        factList.add("Coupez les fleurs fanées de vos plantes pour encourager plus de fleurs et aider à prévenir les problèmes de maladies. Pendant que vous y êtes, assurez-vous d'enlever les feuilles jaunes, brunes ou fanées.")
        return factList
    }
}