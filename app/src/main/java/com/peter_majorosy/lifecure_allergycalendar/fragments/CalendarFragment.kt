package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.adapter.FoodAdapter
import com.peter_majorosy.lifecure_allergycalendar.data.AppDatabase
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var foodadapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_calendar, container, false)

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodadapter = FoodAdapter(view.context)
        rv_calendar.adapter = foodadapter

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Toast.makeText(activity, "$dayOfMonth", Toast.LENGTH_LONG).show()

            val sdf = SimpleDateFormat("yyyy.MM.dd")
            val selectedDate = sdf.format(calendarView.date)


            //AppDatabase.getInstance(view.context).dataDAO().getSpecificFood(selectedDate.toString())
            AppDatabase.getInstance(this.context!!).dataDAO().getAllFood()
            .observe(this, androidx.lifecycle.Observer { items ->
            foodadapter.submitList(items)})
        }
        }
    }
