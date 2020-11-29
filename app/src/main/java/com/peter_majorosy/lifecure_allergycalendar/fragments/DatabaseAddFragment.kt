package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.peter_majorosy.lifecure_allergycalendar.R
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.fragment_databaseadd.*

class DatabaseAddFragment : DialogFragment() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_databaseadd, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_dbcancel.setOnClickListener {
            dismiss()
        }

        btn_dbpublish.setOnClickListener {
            // tv-k tartalmát beírni az adatbázisba
            val foodName: String = et_foodname.text.toString()
            val ingredients: String = et_ingredients.text.toString()

            if(foodName.isEmpty()){
                et_foodname.error = "This field can not be empty."
                et_foodname.requestFocus()
            }else if (ingredients.isEmpty()){
                et_ingredients.error = "This field can not be empty."
                et_ingredients.requestFocus()
            }else{
                saveToFirestore(foodName, ingredients)
            }
        }
    }

    private fun saveToFirestore(foodName: String, ingredients: String) {
        //létrehozom a documentumot, majd később adom hozzá a fieldeket, így az id beírható
        val fooddocref = db.collection("Food").document()
        val food: MutableMap<String, Any> = HashMap()
        food["foodName"] = foodName
        food["ingredients"] = ingredients
        food["id"] = fooddocref.id

        fooddocref.set(food).addOnSuccessListener {
            Toast.makeText(this.context!!,
                "$foodName was added succesfully",
                Toast.LENGTH_SHORT).show()
            et_foodname.text.clear()
            et_ingredients.text.clear()
        }.addOnFailureListener {
            Toast.makeText(this.context!!,
                "Could not add $foodName to database",
                Toast.LENGTH_SHORT).show()
        }
        
        db.collection("User").document(auth.uid!!).get().addOnSuccessListener { snapshot ->
            val author: MutableMap<String, Any> = HashMap()
            author["author"] = snapshot.getString("name")!!
            fooddocref.set(author, SetOptions.merge())
        }

    }
}