package com.example.sunlightdesign.ui.launcher.company.parts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.launcher.company.CompanyViewModel
import kotlinx.android.synthetic.main.fragment_company_contacts.*
import kotlinx.android.synthetic.main.fragment_company_contacts.progress_bar
import kotlinx.android.synthetic.main.fragment_company_market_plan.*

class ContactsCompanyFragment : StrongFragment<CompanyViewModel>(CompanyViewModel::class) {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()

        viewModel.getContactInfoUseCase()

        contactWebView.settings.blockNetworkImage = false
        contactWebView.settings.blockNetworkLoads = false
        contactWebView.settings.loadWithOverviewMode = true

    }

    private fun setObservers() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })
            contentContacts.observe(viewLifecycleOwner, Observer {
                contactWebView.loadData(it, "text/html; charset=utf-8", "UTF-8")
            })
        }
    }

}