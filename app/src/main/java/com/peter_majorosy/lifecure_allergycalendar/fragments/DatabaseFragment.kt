package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
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

        val dbfragment = DatabaseAddFragment()

        foodAddButton.setOnClickListener {

            //TODO: edittext értékét átadni a másik fragmentnek
            //Dialog fragment hívás

            dbfragment.show(fragmentManager!!, "databaseaddfragment")
        }

        foodSearchButton.setOnClickListener {
            //A megadott nevű dokumentum megkeresése
            val db = FirebaseFirestore.getInstance()

            val foodQuery = db.collection("Food").whereEqualTo("foodName", foodname.text.toString())

            foodQuery.get().addOnCompleteListener { p0 ->
                if (p0.isSuccessful) {
                    for (data in p0.result!!) {
                        val foodName = data.get("foodName").toString()
                        val ingredients = data.get("ingredients").toString()

                        tv_resultname.text = foodName
                        tv_resultingredients.text = ingredients
                    }
                } else {
                    Toast.makeText(this.context!!,
                        "Could not find $foodname in the database",
                        Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


}


