package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Office
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.ui.screens.profile.register.adapters.CustomPopupAdapter
import com.example.sunlightdesign.ui.screens.profile.register.adapters.OfficesRecyclerAdapter
import com.example.sunlightdesign.ui.screens.wallet.WalletViewModel
import com.example.sunlightdesign.utils.showMessage
import kotlinx.android.synthetic.main.fragment_register_partner_step_four.*
import kotlinx.android.synthetic.main.fragment_register_partner_step_four.officeDropDownTextView
import java.util.*
import kotlin.collections.ArrayList


class RegisterFragmentStepFour : StrongFragment<ProfileViewModel>(ProfileViewModel::class),
    OfficesRecyclerAdapter.OfficeSelector {

    private val officesRecyclerAdapter by lazy {
        return@lazy OfficesRecyclerAdapter(requireContext(), this)
    }
    private lateinit var citiesAdapter: CustomPopupAdapter<WalletViewModel.ShortenedCity>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_partner_step_four, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()
        setObservers()

        fragment_register_partner_step_four_rv.layoutManager = LinearLayoutManager(requireContext())
        fragment_register_partner_step_four_rv.adapter = officesRecyclerAdapter

        viewModel.getOfficesList()
    }

    private fun setObservers() {
        viewModel.apply {
            officeList.observe(viewLifecycleOwner, Observer {
                it.offices?.let {offices ->
                    officesRecyclerAdapter.setItems(offices as ArrayList<Office?>)
                    val citiesList = ArrayList<WalletViewModel.ShortenedCity>()
                    offices.forEach { office ->
                        office?.city?.id ?: return@forEach
                        citiesList.add(
                            WalletViewModel.ShortenedCity(
                                office.city.id,
                                office.city.city_name.toString()
                            )
                        )
                    }
                    initCities(offices, citiesList)
                }
            })
        }
    }

    private fun setListeners() {
        btn_next_step_four.setOnClickListener {
            if (!checkFields()) return@setOnClickListener
            findNavController().navigate(R.id.action_stepFourFragment_to_stepFiveFragment)
        }

        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initCities(
        officesList: List<Office?>,
        citiesList: ArrayList<WalletViewModel.ShortenedCity>
    ) {
        citiesAdapter = CustomPopupAdapter(
            context = requireContext(),
            items = citiesList,
            valueChecker = object : CustomPopupAdapter.ValueChecker<WalletViewModel.ShortenedCity, String>{
                override fun filter(
                    value: WalletViewModel.ShortenedCity,
                    subvalue: String?
                ): Boolean {
                    val v = value.city_name
                    if (subvalue == null || subvalue.isBlank())
                        return true
                    return v.toLowerCase(Locale.getDefault()).startsWith(subvalue)
                }

                override fun toString(value: WalletViewModel.ShortenedCity?): String =
                    value?.city_name.toString()

                override fun toLong(value: WalletViewModel.ShortenedCity?): Long =
                    value?.city_id?.toLong() ?: -1

            }
        )

        officeDropDownTextView.setAdapter(citiesAdapter)
        officeDropDownTextView.setOnItemClickListener { parent, view, position, id ->
            val adapter = officeDropDownTextView.adapter
            val c = adapter.getItem(position) as WalletViewModel.ShortenedCity
            val officesWithCityId = ArrayList<Office?>()
            officesList.forEach {
                if (it?.city_id == c.city_id) {
                    officesWithCityId.add(it)
                }
            }
            officesRecyclerAdapter.setItems(officesWithCityId)
        }
    }

    override fun onOfficeSelected(id: Int) {
        viewModel.createOrderPartnerBuilder.officeId = id
//            viewModel.officeList.value?.offices?.get(id)?.id ?: -1
//            officesRecyclerAdapter.getItems().firstOrNull { id == it?.id }?.id ?: -1
    }

    private fun checkFields() : Boolean {
        val message = when (viewModel.createOrderPartnerBuilder.officeId) {
            -1 -> "Выберите офис"
            else -> null
        }
        if (message != null) {
            showMessage(
                context = requireContext(),
                message = message
            )
            return false
        }
        return true
    }

}
