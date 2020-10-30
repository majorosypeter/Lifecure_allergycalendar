package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.peter_majorosy.lifecure_allergycalendar.LoginActivity
import com.peter_majorosy.lifecure_allergycalendar.MainActivity
import com.peter_majorosy.lifecure_allergycalendar.R
import com.peter_majorosy.lifecure_allergycalendar.TestActivity
import com.peter_majorosy.lifecure_allergycalendar.data.AppDatabase
import com.peter_majorosy.lifecure_allergycalendar.data.FoodModel
import com.peter_majorosy.lifecure_allergycalendar.data.SymptomModel
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

        btnAddFood.setOnClickListener {

            val sdf = SimpleDateFormat("yyyy.MM.dd")
            val currentDate = sdf.format(Date())

            val food = FoodModel(null, ateToday.text.toString(), currentDate.toString())

            if (ateToday.text.isEmpty()) {
                ateToday.error = "This field can not be empty"
            } else {
                Thread {
                    AppDatabase.getInstance(view.context).dataDAO().insertFood(food)
                }.start()
                ateToday.text.clear()
                Toast.makeText(activity, "Succesfully added to calendar", Toast.LENGTH_SHORT).show()
            }
        }

        btnAddSymptom.setOnClickListener {

            val sdf = SimpleDateFormat("yyyy.MM.dd")
            val currentDate = sdf.format(Date())

            val symptom = SymptomModel(null, symptoms.text.toString(), currentDate.toString())

            if(symptoms.text.isEmpty()){
                symptoms.error = "This field can not be empty"
            } else {
                Thread {
                    AppDatabase.getInstance(view.context).dataDAO().insertSymptom(symptom)
                }.start()
                symptoms.text.clear()
                Toast.makeText(activity, "Succesfully added to calendar", Toast.LENGTH_SHORT).show()        //Issuccesful?
            }

        }

        btn_logout.setOnClickListener {
            var firebaseuser = auth.currentUser
            auth.signOut()
            startActivity(Intent(this.context, LoginActivity::class.java))
        }

        btn_test.setOnClickListener {
            startActivity(Intent(this.context, TestActivity::class.java))
        }

    }
}