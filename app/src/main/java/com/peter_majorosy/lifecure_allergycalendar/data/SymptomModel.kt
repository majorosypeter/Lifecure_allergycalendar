package com.peter_majorosy.lifecure_allergycalendar.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Symptom")
data class SymptomModel (
    @PrimaryKey(autoGenerate = true) var symptomId: Long?,
    @ColumnInfo(name = "symptoms") var symptom: String,
    @ColumnInfo(name = "date") var date: String
)