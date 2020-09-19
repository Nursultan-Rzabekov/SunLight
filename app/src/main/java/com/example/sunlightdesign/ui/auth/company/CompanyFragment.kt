

package com.example.sunlightdesign.ui.auth.company

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.sunlightdesign.BaseApplication
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.auth.adapter.BannerViewPagerAdapter
import kotlinx.android.synthetic.main.glavnaia_avtorizovannyi.*
import kotlinx.android.synthetic.main.sunlight_banner.*
import javax.inject.Inject


@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class CompanyFragment : BaseCompanyFragment() {

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

        setListeners()

//        viewModel.getUseCase()
    }


    private fun setListeners(){

    }

}
