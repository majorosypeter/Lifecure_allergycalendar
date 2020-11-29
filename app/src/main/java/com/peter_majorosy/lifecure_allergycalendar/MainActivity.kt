package com.peter_majorosy.lifecure_allergycalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.peter_majorosy.lifecure_allergycalendar.fragments.CalendarFragment
import com.peter_majorosy.lifecure_allergycalendar.fragments.DatabaseFragment
import com.peter_majorosy.lifecure_allergycalendar.fragments.HomeFragment
import com.peter_majorosy.lifecure_allergycalendar.fragments.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val calendarFragment = CalendarFragment()
        val foodFragment = DatabaseFragment()
        val profileFragment = ProfileFragment()

        bottomnav.selectedItemId = R.id.home

        makeCurrentFragment(homeFragment)

        bottomnav.setOnNavigationItemSelectedListener {
            //kattintásnak megfelelően fl_wrapper helyére fragment betöltése
            when (it.itemId) {
                R.id.home -> makeCurrentFragment(homeFragment)
                R.id.calendar -> makeCurrentFragment(calendarFragment)
                R.id.database -> makeCurrentFragment(foodFragment)
                R.id.profile -> makeCurrentFragment(profileFragment)
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

}
