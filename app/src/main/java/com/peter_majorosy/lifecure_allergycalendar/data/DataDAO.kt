package com.peter_majorosy.lifecure_allergycalendar.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface DataDAO {
    @Query("SELECT * FROM Food WHERE date = :date")
    fun getSpecificFood(date: String): List<FoodModel>

    @Query("SELECT * FROM Symptom WHERE date = :date")
    fun getSpecificSymptom(date: String): List<SymptomModel>

    @Insert
    fun insertFood(data: FoodModel)

    @Insert
    fun insertSymptom(data: SymptomModel)


    @Delete
    fun deleteData(data: FoodModel)
}