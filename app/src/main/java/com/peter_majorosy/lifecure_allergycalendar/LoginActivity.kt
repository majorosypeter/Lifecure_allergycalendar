package com.peter_majorosy.lifecure_allergycalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        logbutton.setOnClickListener {
            startSignIn()
        }

        notreg.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
    }

    public override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        signIn(user)
    }

    private fun startSignIn() {
        if (logemail.text.toString().isEmpty()) {
            logemail.error = "This field can not be empty"
            logemail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(logemail.text.toString()).matches()) {
            logemail.error = "Please enter a valid email"
            logemail.requestFocus()
            return
        }
        if (logpw.text.toString().isEmpty()) {
            logpw.error = "Please enter password."
            logpw.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(logemail.text.toString(), logpw.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    signIn(user)
                } else {
                    Toast.makeText(baseContext,
                        "Login failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signIn(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}