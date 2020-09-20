package com.example.sunlightdesign.ui.launcher.company

import android.os.Bundle
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.ui.launcher.company.adapter.CompanyInfoAdapter
import com.example.sunlightdesign.ui.launcher.company.parts.AboutCompanyFragment
import com.example.sunlightdesign.ui.launcher.company.parts.ContactsCompanyFragment
import com.example.sunlightdesign.ui.launcher.company.parts.MarketPlanFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_company_info.*
import javax.inject.Inject

class CompanyActivity : StrongActivity()
{
    private lateinit var viewPagerAdapter: CompanyInfoAdapter

    override val layoutId: Int
        get() = R.layout.activity_company_info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(company_toolbar)
        company_toolbar.setNavigationIcon(R.drawable.ic_close)
        company_toolbar.setNavigationOnClickListener { finish() }

        initViewPager()

        TabLayoutMediator(company_tablayout, company_viewpager) {
                tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.about_company)
                }
                1 -> {
                    tab.text = getString(R.string.market_plan)
                }
                2 -> {
                    tab.text = getString(R.string.сontacts)
                }
            }
        }.attach()
    }

    private fun initViewPager() {
        viewPagerAdapter = CompanyInfoAdapter(supportFragmentManager, lifecycle,
                listOf(
                    AboutCompanyFragment(),
                    MarketPlanFragment(),
                    ContactsCompanyFragment()
                )
            )
        company_viewpager.adapter = viewPagerAdapter
    }
}

















