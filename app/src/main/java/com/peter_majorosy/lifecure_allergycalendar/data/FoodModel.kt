package com.peter_majorosy.lifecure_allergycalendar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Food")
data class FoodModel (
    @PrimaryKey(autoGenerate = true) var foodId: Long?,
    @ColumnInfo(name = "foodName") var foodName: String,
    @ColumnInfo(name = "date") var date: String
    )