package com.peter_majorosy.lifecure_allergycalendar.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DataDAO {
    @Query("SELECT * FROM Data WHERE date = :date")
    fun getSpecificData(date: String): LiveData<List<DataModel>>

    @Query("SELECT COUNT(dataName) FROM Data WHERE date = :date AND isFood = '1'")
    fun getFoodCount(date: String): Float

    @Query("SELECT COUNT(dataName) FROM Data WHERE date = :date AND isFood = '0'")
    fun getSymptomCount(date: String): Float

    @Query("SELECT MIN(date) FROM Data")
    fun getMinDate(): String

    @Insert
    fun insertData(data: DataModel)

    @Delete
    fun deleteData(data: DataModel)

}