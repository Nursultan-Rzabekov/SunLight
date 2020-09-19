

package com.example.sunlightdesign.ui.launcher.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sunlightdesign.R


@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class CompanyFragment : BaseCompanyFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lichnyi_kabinet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()


    }

    private fun setListeners(){

    }

}
