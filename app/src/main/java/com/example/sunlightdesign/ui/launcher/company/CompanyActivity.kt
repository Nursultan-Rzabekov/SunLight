package com.example.sunlightdesign.ui.launcher.company

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.ui.launcher.company.parts.AboutCompanyFragment
import com.example.sunlightdesign.ui.launcher.company.parts.ContactsCompanyFragment
import com.example.sunlightdesign.ui.launcher.company.parts.MarketPlanFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_company_info.*
import javax.inject.Inject

class CompanyActivity : StrongActivity(), NavController.OnDestinationChangedListener
{
    private lateinit var viewPagerAdapter: CompanyInfoAdapter

    @Inject
    private val aboutCompanyFragment = AboutCompanyFragment()

    @Inject
    private val contactsCompanyFragment = ContactsCompanyFragment()

    @Inject
    private val marketPlanFragment = MarketPlanFragment()

    override val layoutId: Int
        get() = R.layout.activity_company

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(company_toolbar)
        company_toolbar.setNavigationIcon(R.drawable.ic_close)
        company_toolbar.setNavigationOnClickListener { finish() }

        initViewPager()

        TabLayoutMediator(company_tablayout, company_viewpager) {
                tab, _ ->
            tab.text = "${tab.position}"
        }.attach()

        findNavController(R.id.company_nav_host_fragment).addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }

    private fun initViewPager() {
        viewPagerAdapter = CompanyInfoAdapter(supportFragmentManager, lifecycle,
                listOf(
                   aboutCompanyFragment,
                    marketPlanFragment,
                    contactsCompanyFragment
                )
            )
        company_viewpager.adapter = viewPagerAdapter
    }
}

















