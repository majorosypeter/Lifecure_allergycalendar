package com.peter_majorosy.lifecure_allergycalendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.peter_majorosy.lifecure_allergycalendar.fragments.CalendarFragment
import com.peter_majorosy.lifecure_allergycalendar.fragments.FoodFragment
import com.peter_majorosy.lifecure_allergycalendar.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val calendarFragment = CalendarFragment()
        val foodFragment = FoodFragment()

        bottomnav.selectedItemId

        makeCurrentFragment(homeFragment)

        bottomnav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> makeCurrentFragment(homeFragment)
                R.id.calendar -> makeCurrentFragment(calendarFragment)
                R.id.database -> makeCurrentFragment(foodFragment)
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
