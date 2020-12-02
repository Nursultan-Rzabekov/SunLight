package com.example.sunlightdesign.ui.launcher.company.parts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.launcher.company.CompanyViewModel
import kotlinx.android.synthetic.main.fragment_company_market_plan.*
import kotlinx.android.synthetic.main.fragment_company_market_plan.progress_bar
import kotlinx.android.synthetic.main.news_activity.*

class MarketPlanFragment : StrongFragment<CompanyViewModel>(CompanyViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_market_plan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setObservers()

        viewModel.getMarketInfoUseCase()

        marketingWebView.settings.javaScriptEnabled = true
        marketingWebView.settings.loadsImagesAutomatically = true
        marketingWebView.settings.loadWithOverviewMode = true
        marketingWebView.settings.useWideViewPort = true
        marketingWebView.settings.domStorageEnabled = true

        marketingWebView.settings.textZoom = 260
    }

    private fun setObservers() {

        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })
            contentMarket.observe(viewLifecycleOwner, Observer {
                marketingWebView.loadData(it, "text/html; charset=utf-8", "UTF-8")
            })

        }
    }

}