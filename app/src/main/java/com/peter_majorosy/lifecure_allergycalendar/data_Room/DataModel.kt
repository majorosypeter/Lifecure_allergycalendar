package com.peter_majorosy.lifecure_allergycalendar.data_Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Data")
data class DataModel(
    @PrimaryKey(autoGenerate = true) var dataId: Long?,
    @ColumnInfo(name = "dataName") var dataName: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "isFood") var isFood: Boolean,
    @ColumnInfo(name = "severity") var severity: Int?
) : Serializable