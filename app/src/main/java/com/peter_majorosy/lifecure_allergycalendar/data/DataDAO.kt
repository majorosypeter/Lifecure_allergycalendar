package com.peter_majorosy.lifecure_allergycalendar.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DataDAO {
    @Query("SELECT * FROM Food WHERE date = :date")
    fun getSpecificFood(date: String): LiveData<List<FoodModel>>

    @Query("SELECT * FROM Food")
    fun getAllFood(): LiveData<List<FoodModel>>

    @Query("SELECT * FROM Symptom WHERE date = :date")
    fun getSpecificSymptom(date: String): LiveData<List<SymptomModel>>

    @Insert
    fun insertFood(data: FoodModel)

    @Insert
    fun insertSymptom(data: SymptomModel)

    @Delete
    fun deleteData(data: FoodModel)
}