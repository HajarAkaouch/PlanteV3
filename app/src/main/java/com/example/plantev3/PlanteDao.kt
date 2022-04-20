package com.example.plantev3

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanteDao {
    @Insert
    suspend fun insert(plantePreference: PlantePreference )

    @Update
    suspend fun update(plantePreference: PlantePreference)

    @Delete
    suspend fun delete(plantePreference: PlantePreference)

    @Query("SELECT * FROM `pref-plante`")
    fun fetchAllPref():Flow<List<PlantePreference>>

    @Query("SELECT * FROM `pref-plante` where id=:id")
    fun fetchPrefById(id:Int):Flow<List<PlantePreference>>
}