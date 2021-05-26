package com.corp.sunlightdesign.ui.screens.order.market

import android.os.Bundle
import androidx.navigation.Navigation
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.ui.base.StrongActivity


class MarketActivity : StrongActivity() {

    override val layoutId: Int
        get() = R.layout.activity_market

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = Navigation.findNavController(this, R.id.market_nav_host_fragment)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_market_graph)

        intent.extras?.getInt("destination")?.let {
            if (it == 0) {
                navGraph.startDestination = R.id.myTicketsFragment
            } else {
                navGraph.startDestination = R.id.market_fragment
            }
        }

        navController.graph = navGraph
    }
}