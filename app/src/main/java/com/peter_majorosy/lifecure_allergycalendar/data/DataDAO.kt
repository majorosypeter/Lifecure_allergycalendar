package com.peter_majorosy.lifecure_allergycalendar.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface DataDAO {
    @Query("SELECT * FROM Data WHERE date = :date")
    fun getSpecificData(date: String): LiveData<List<DataModel>>

    @Query("SELECT * FROM Data")
    fun getAllData(): LiveData<List<DataModel>>

    @Insert
    fun insertData(data: DataModel)

    @Delete
    fun deleteData(data: DataModel)

}