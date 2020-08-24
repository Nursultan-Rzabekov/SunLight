package com.example.sunlightdesign.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.utils.BottomNavController
import com.example.sunlightdesign.utils.setUpNavigation
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : StrongActivity(),
    BottomNavController.NavGraphProvider,
    BottomNavController.OnNavigationGraphChanged,
    BottomNavController.OnNavigationReselectedListener {

    override val layoutId: Int
        get() = R.layout.activity_main

    private lateinit var bottomNavigationView: BottomNavigationView

    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
            this,
            R.id.main_nav_host_fragment,
            R.id.nav_home,
            this,
            this)
    }

//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Locale.setDefault(Locale.forLanguageTag("ru"))

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setUpNavigation(bottomNavController, this)
        if (savedInstanceState == null) {
            bottomNavController.onNavigationItemSelected()
        }

        //subscribeObservers()

    }

    override fun getNavGraphId(itemId: Int) = when(itemId){
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
        TODO("Not yet implemented")
    }

    override fun onReselectNavItem(navController: NavController, fragment: Fragment) {
        TODO("Not yet implemented")
    }
}