package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.peter_majorosy.lifecure_allergycalendar.LoginActivity
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.data.AppDatabase
import com.peter_majorosy.lifecure_allergycalendar.data.DataModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val currentDate = sdf.format(Date())

        seekBar.setOnSeekBarChangeListener(object: OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tv_seekbar.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnAddFood.setOnClickListener {
            addFood(currentDate)
        }

        btnAddSymptom.setOnClickListener {
            addSymptom(currentDate)
        }

        btn_logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this.context, LoginActivity::class.java))
            activity?.finish()
        }


    }

    private fun addSymptom(currentDate: String) {
        val data =
            DataModel(null, symptoms.text.toString(), currentDate.toString(), isFood = false,
                severity = tv_seekbar.text.toString().toInt())

        if (symptoms.text.isEmpty()) {
            symptoms.error = "This field can not be empty"
        } else if (tv_seekbar.text.toString() == "0")
        {
            tv_seekbar.error = "Please choose severity between 1 to 10"
        } else {
            addData(data)
            symptoms.text.clear()
            seekBar.progress = 0
        }
    }

    private fun addFood(currentDate: String) {
        val data =
            DataModel(null, ateToday.text.toString(), currentDate, isFood = true,
                severity = null)

        if (ateToday.text.isEmpty()) {
            ateToday.error = "This field can not be empty"
        } else {
            addData(data)
            ateToday.text.clear()
        }
    }

    private fun addData(data: DataModel) {
        Thread {
            AppDatabase.getInstance(this.context!!).dataDAO().insertData(data)
        }.start()
        Toast.makeText(activity, "Succesfully added to calendar", Toast.LENGTH_SHORT).show()
    }
}