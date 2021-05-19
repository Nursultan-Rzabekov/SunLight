package com.corp.sunlightdesign.ui.launcher.company.parts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.ui.launcher.company.BaseCompanyFragment


class AboutCompanyFragment : BaseCompanyFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_company_about, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()

//        viewModel.getUseCase()
    }


    private fun setListeners() {

    }

}
