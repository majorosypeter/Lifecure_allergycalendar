package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.adapter.RvAdapter
import com.peter_majorosy.lifecure_allergycalendar.data.AppDatabase
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat

class CalendarFragment : Fragment() {

    private lateinit var adapter: RvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RvAdapter(view.context)
        rv_calendar.adapter = adapter
        rv_calendar.layoutManager =
            LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val perfectMonth: String
            val monthbugfix = month + 1         //2020.0.1 = január 1

            val day = if (dayOfMonth < 10) "0$dayOfMonth"
            else "$dayOfMonth"
            perfectMonth = if (monthbugfix < 10) "0$monthbugfix"
            else "$monthbugfix"
            val dateselected = "$year.$perfectMonth.$day"

            //formátum tesztelése
            Log.d("dateselected", dateselected)


            AppDatabase.getInstance(this.context!!).dataDAO().getSpecificData(dateselected)
                .observe(this, androidx.lifecycle.Observer { items ->
                    adapter.submitList(items)
                })
        }
    }
}
