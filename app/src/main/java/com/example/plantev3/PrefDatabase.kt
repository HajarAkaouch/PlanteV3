package com.example.plantev3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlantePreference::class], version = 1)
abstract class PrefDatabase:RoomDatabase() {

    abstract fun employeeDao():PlanteDao

    companion object{
        @Volatile
        private var INSTANCE: PrefDatabase? = null

        fun getInstance(context: Context):PrefDatabase{

            synchronized(this){
                var instance = INSTANCE

                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PrefDatabase::class.java,
                        "preference_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                    return instance
            }

        }

    }
}
