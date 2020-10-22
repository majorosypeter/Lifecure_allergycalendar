package com.peter_majorosy.lifecure_allergycalendar.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FoodModel::class, SymptomModel::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataDAO() : DataDAO

    companion object{
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "data.db").fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}