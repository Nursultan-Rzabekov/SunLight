package com.example.sunlightdesign.ui.screens.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.launcher.company.CompanyActivity
import kotlinx.android.synthetic.main.launcher_authenticated.*


class HomeFragment : Fragment() {


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.launcher_authenticated, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_enter_cv.visibility = View.GONE
        btn_structure_cv.visibility = View.VISIBLE

        btn_company_cv.setOnClickListener {
            startActivity(Intent(context, CompanyActivity::class.java))
        }

    }

}
