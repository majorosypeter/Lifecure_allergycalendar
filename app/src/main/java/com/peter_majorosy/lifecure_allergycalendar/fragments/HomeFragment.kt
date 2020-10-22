package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.data.AppDatabase
import com.peter_majorosy.lifecure_allergycalendar.data.FoodModel
import com.peter_majorosy.lifecure_allergycalendar.data.SymptomModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAddFood.setOnClickListener {

            val sdf = SimpleDateFormat("yyyy.MM.dd")
            val currentDate = sdf.format(Date())

            val food = FoodModel(null, ateToday.text.toString(), currentDate.toString())


            val dbThread = Thread {
                AppDatabase.getInstance(this.context!!).dataDAO().insertFood(food)
            }
            dbThread.start()

        }

        btnAddSymptom.setOnClickListener {

            val sdf = SimpleDateFormat("yyyy.MM.dd")
            val currentDate = sdf.format(Date())

            val symptom = SymptomModel(null, symptoms.text.toString(), currentDate.toString())

            val dbThread = Thread {
                AppDatabase.getInstance(this.context!!).dataDAO().insertSymptom(symptom)
            }
            dbThread.start()

        }
    }
}