package com.peter_majorosy.lifecure_allergycalendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_database.*
import kotlinx.android.synthetic.main.fragment_databaseadd.*
import kotlinx.android.synthetic.main.fragment_home.*

class DatabaseAddFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_databaseadd, container,false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_dbcancel.setOnClickListener {
            dismiss()
        }

        btn_dbadd.setOnClickListener {
            // tv-k tartalmát beírni az adatbázisba
        }
    }
}