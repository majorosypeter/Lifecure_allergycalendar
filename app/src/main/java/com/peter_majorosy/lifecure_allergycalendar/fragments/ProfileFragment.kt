package com.peter_majorosy.lifecure_allergycalendar.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.peter_majorosy.lifecure_allergycalendar.LoginActivity
import com.peter_majorosy.lifecure_allergycalendar.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private var auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val ref = db.collection("User")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_email.text = auth.currentUser?.email
        ref.document(auth.currentUser?.uid.toString()).get().addOnSuccessListener { snapshot ->
            tv_profile.text = snapshot["name"].toString()
        }

        btn_logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this.context, LoginActivity::class.java))
            activity?.finish()
        }
    }

}