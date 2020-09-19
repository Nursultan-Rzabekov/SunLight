

package com.example.sunlightdesign.ui.auth

import android.content.Context
import android.content.Intent
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
import com.example.sunlightdesign.ui.auth.company.CompanyActivity
import kotlinx.android.synthetic.main.glavnaia_avtorizovannyi.*
import kotlinx.android.synthetic.main.sunlight_banner.*
import javax.inject.Inject


@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class AuthFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<AuthViewModel> { viewModelFactory }


    private val handler = Handler()
    private val delay = 3000L //milliseconds

    private val viewPager: ViewPager? = null
    private var page = 0
    private var newsViewPagerAdapter: BannerViewPagerAdapter? = null

    var runnable: Runnable = object : Runnable {
        override fun run() {
            if (newsViewPagerAdapter?.count === page) {
                page = 0
            } else {
                page++
            }
            news_view_pager.setCurrentItem(page, true)
            handler.postDelayed(this, delay)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as BaseApplication).appComponent.addAuthComponent().create().inject(this)
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
        btn_enter_cv.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_loginFragment)
        }

        btn_company_cv.setOnClickListener {
            startActivity(Intent(context,CompanyActivity::class.java))
        }

        btn_structure_cv.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_loginFragment)
        }

        btn_market_cv.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_loginFragment)
        }
    }

}
