

package com.example.sunlightdesign.ui.screens.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.sunlightdesign.BaseApplication
import com.example.sunlightdesign.R
import kotlinx.android.synthetic.main.glavnaia_avtorizovannyi.*



class HomeFragment : Fragment() {



    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.glavnaia_avtorizovannyi, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_enter_cv.visibility = View.GONE
        btn_structure_cv.visibility = View.VISIBLE



    }

}
