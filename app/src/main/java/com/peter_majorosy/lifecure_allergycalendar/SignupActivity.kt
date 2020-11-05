package com.peter_majorosy.lifecure_allergycalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        regbutton.setOnClickListener {
            regUser()
        }

        regalready.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun regUser() {
        if (regemail.text.toString().isEmpty()) {
            regemail.error = "Please enter email."
            regemail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(regemail.text.toString()).matches()) {
            regemail.error = "Please enter a valid email."
            regemail.requestFocus()
            return
        }
        if (regpw1.text.toString().isEmpty()) {
            regpw1.error = "Please enter password."
            regpw1.requestFocus()
            return
        }
        if (regpw1.text.toString().length < 6) {
            regpw1.error = "Password has to be at least 6 characters long."
            regpw1.requestFocus()
            return
        }
        if (!regpw1.text.toString().equals(regpw2.text.toString())) {
            regpw2.error = "Those passwords didn't match. Try again."
            regpw2.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(regemail.text.toString(), regpw1.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Registration failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}