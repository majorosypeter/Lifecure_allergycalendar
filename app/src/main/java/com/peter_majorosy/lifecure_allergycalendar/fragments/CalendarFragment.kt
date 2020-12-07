package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.adapter.RvAdapter
import com.peter_majorosy.lifecure_allergycalendar.data_Room.AppDatabase
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private lateinit var adapter: RvAdapter

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val currentDate = sdf.format(Date())

        loadList(currentDate)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RvAdapter(view.context)
        rv_calendar.adapter = adapter
        rv_calendar.layoutManager =
            LinearLayoutManager(this.context!!, LinearLayoutManager.VERTICAL, false)

        //Dátum kiválasztása
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val day = if (dayOfMonth < 10) "0$dayOfMonth"
            else "$dayOfMonth"

            val increasedMonth = month + 1         //2020.0.1 = január 1
            val perfectMonth = if (increasedMonth < 10) "0$increasedMonth"
            else "$increasedMonth"

            val dateSelected = "$year.$perfectMonth.$day"

            //Formátum tesztelése
            Log.d("dateselected", dateSelected)

            loadList(dateSelected)
        }
    }

    //Lista feltöltése és átadása az adapternek
    private fun loadList(dateSelected: String) {
        AppDatabase.getInstance(this.context!!).dataDAO().getSpecificData(dateSelected)
            .observe(this, Observer { items ->
                adapter.submitList(items)
            })
    }
}
