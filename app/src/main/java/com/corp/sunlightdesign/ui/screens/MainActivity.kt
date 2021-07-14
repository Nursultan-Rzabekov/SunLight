package com.corp.sunlightdesign.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.ui.base.StrongActivity
import com.corp.sunlightdesign.utils.BottomNavController
import com.corp.sunlightdesign.utils.setUpNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : StrongActivity(),
    BottomNavController.NavGraphProvider,
    BottomNavController.OnNavigationGraphChanged,
    BottomNavController.OnNavigationReselectedListener {

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    private lateinit var bottomNavigationView: BottomNavigationView

    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
            this,
            R.id.fragment_container,
            R.id.nav_home,
            this,
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setUpNavigation(bottomNavController, this)
        if (savedInstanceState == null) {
            bottomNavController.onNavigationItemSelected()
        }

    }

    override fun getNavGraphId(itemId: Int) = when (itemId) {
        R.id.nav_home -> {
            R.navigation.navigate_home
        }
        R.id.nav_profile -> {
            R.navigation.navigate_profile
        }
        R.id.nav_wallet -> {
            R.navigation.navigate_wallet
        }
        R.id.nav_list -> {
            R.navigation.navigate_list
        }
        R.id.nav_email -> {
            R.navigation.navigate_email
        }
        else -> {
            R.navigation.navigate_home
        }
    }

    override fun onGraphChange() {
    }

    override fun onReselectNavItem(navController: NavController, fragment: Fragment) {
    }
}