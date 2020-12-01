package com.example.sunlightdesign.ui.launcher.company.parts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.launcher.company.BaseCompanyFragment
import kotlinx.android.synthetic.main.activity_users_tree.*
import kotlinx.android.synthetic.main.fragment_company_market_plan.*

class MarketPlanFragment : BaseCompanyFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_market_plan, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        marketingWebView.settings.loadWithOverviewMode = true
        marketingWebView.settings.useWideViewPort = true
        marketingWebView.settings.javaScriptEnabled = true
        marketingWebView.settings.loadsImagesAutomatically = true
        marketingWebView.settings.builtInZoomControls = true
        marketingWebView.settings.domStorageEnabled = true

        marketingWebView.loadUrl("http://test-sunlight.qsmart.kz/marketing-plan")
    }

}