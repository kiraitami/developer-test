package com.example.developertest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.developertest.R
import com.example.developertest.ui.fragments.PicturesFragment
import com.example.developertest.ui.fragments.UsersFragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigation()
        changeFragment(UsersFragment())
    }

    private fun initBottomNavigation(){
        main_bottom_nav_view.setOnNavigationItemSelectedListener(this)
    }

    private fun changeFragment(fragment: Fragment){

        supportActionBar?.title = resources.getText(
            if (fragment is UsersFragment)
                R.string.toolbar_title_users
            else
                R.string.toolbar_title_photos
        )

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame_layout, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.nav_users -> {
                changeFragment(UsersFragment())
                return true
            }
            R.id.nav_pictures -> {
                changeFragment(PicturesFragment())
                return true
            }
        }
        return false
    }
}
