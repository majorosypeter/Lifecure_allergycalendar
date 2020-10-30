package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.peter_majorosy.lifecure_allergycalendar.DatabaseAddFragment
import com.peter_majorosy.lifecure_allergycalendar.R
import kotlinx.android.synthetic.main.fragment_database.*

class DatabaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_database, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var dbfragment = DatabaseAddFragment()

        foodAddButton.setOnClickListener {
            //Dialog fragment hívás, foodname et átadása?
            dbfragment.show(fragmentManager!!, "databaseaddfragment")
        }

        foodSearchButton.setOnClickListener {
            //Kilistázás valahogyan
        }
    }
}
