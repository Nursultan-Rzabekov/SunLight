package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.DeliveryInfoRequest
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliveryServiceResponse
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.usecase.usercase.orders.CalculateDeliveryUseCase
import com.example.sunlightdesign.utils.*
import kotlinx.android.synthetic.main.delivery_type_company.view.*
import kotlinx.android.synthetic.main.fragment_register_delivery_service_step_four.*
import java.util.*
import kotlin.collections.ArrayList

class DeliveryServiceRegisterStepFourFragment :
    StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

    private val countriesPopUpAdapter = BasePopUpAdapter<Country>()
    private val regionsPopUpAdapter = BasePopUpAdapter<Region>()
    private val citiesPopUpAdapter = BasePopUpAdapter<City>()

    private var countryId: Int = -1
    private var regionId: Int = -1
    private var cityId: Int = -1
    private var countryCode: String = ""

    private var selectedDeliveryService: DeliveryServiceResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_register_delivery_service_step_four,
            container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        nullifyAll()
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() = with(viewModel) {
        countriesList.observe(viewLifecycleOwner, Observer {
            setupLists(it)
        })
        deliverService.observe(viewLifecycleOwner, Observer {
            nullifyDelivery()
            recycleDeliveryServices(it.deliveryServices.orEmpty())
        })
    }

    private fun nullifyAll() {
        countryTextView.text = ""
        regionsTextView.text = ""
        citiesTextView.text = ""
        countryId = -1
        regionId = -1
        cityId = -1
        nullifyDelivery()
    }

    private fun nullifyDelivery() {
        selectedDeliveryService = null
        deliveryTypeLinearLayout.removeAllViews()
        deliverySectionLinearLayout.isVisible = false
        deliverServiceTypeLinearLayout.isVisible = false
    }

    private fun setCountriesList(list: ArrayList<Country>) {
        countryTextView.setOnClickListener {
            showListPopupWindow(
                context = requireContext(),
                items = list,
                anchor = countryTextView,
                adapter = countriesPopUpAdapter
            ) { country ->
                countryId = country.id ?: -1
                regionId = -1
                cityId = -1
                countryCode = country.country_code.orEmpty()
                regionsTextView.text = ""
                citiesTextView.text = ""
                countryTextView.text = country.country_name
                nullifyDelivery()
            }
        }
    }

    private fun setRegionsList(list: ArrayList<Region>) {
        regionsTextView.setOnClickListener {
            showListPopupWindow(
                context = requireContext(),
                items = list.filter { countryId == it.country_id || countryId == -1 },
                anchor = regionsTextView,
                adapter = regionsPopUpAdapter
            ) { region ->
                regionId = region.id ?: -1
                cityId = -1
                citiesTextView.text = ""
                regionsTextView.text = region.region_name
                nullifyDelivery()
            }
        }
    }

    private fun setCitiesList(list: ArrayList<City>) {
        citiesTextView.setOnClickListener {
            showListPopupWindow(
                context = requireContext(),
                items = list.filter { regionId == it.region_id || regionId == -1 },
                anchor = citiesTextView,
                adapter = citiesPopUpAdapter
            ) { city ->
                cityId = city.id ?: -1
                citiesTextView.text = city.city_name
                nullifyDelivery()
                fetchDeliveryServices()
            }
        }
    }

    private fun fetchDeliveryServices() {
        if (countryId == -1 || regionId == -1 || cityId == -1) return
        viewModel.calculateDelivery(
            CalculateDeliveryUseCase.Request(
                cityId = cityId,
                countryCode = countryCode,
                totalAmount = Product.getTotalSum(viewModel.createOrderPartnerBuilder.products),
                weight = Product.getTotalWeight(viewModel.createOrderPartnerBuilder.products)
                    .toString()
            )
        )
    }

    private fun setupLists(countriesList: CountriesList) {
        setCountriesList(ArrayList(countriesList.countries.orEmpty()))
        setRegionsList(ArrayList(countriesList.regions.orEmpty()))
        setCitiesList(ArrayList(countriesList.cities.orEmpty()))
    }

    private fun checkFields(): Boolean {
        return countryId != -1 &&
                cityId != -1 &&
                !deliveryTypeLinearLayout.isEmpty() &&
                regionId != -1
    }

    private fun setupListeners() {
        btn_next_step_four.setOnClickListener {
            if (!checkFields()) return@setOnClickListener showMessage(
                requireContext(),
                message = getString(R.string.fill_all_the_field)
            )

            val fixDelivery = selectedDeliveryService
            fixDelivery ?: return@setOnClickListener showMessage(
                requireContext(),
                message = "Delivery Service is not found"
            )

            viewModel.createOrderPartnerBuilder.deliveryInfo = DeliveryInfoRequest(
                delivery_type_id = fixDelivery.deliveryTypeId.orMinusOne(),
                delivery_zone_id = fixDelivery.deliveryZoneId.orMinusOne(),
                price = fixDelivery.price.orZero(),
                weight = Product.getTotalWeight(viewModel.createOrderPartnerBuilder.products)
                    .toString(),
                city_id = cityId,
                region_id = regionId,
                country_code = countryCode,
                country_id = countryId,
                address = "",
                fio = ""
            )
            viewModel.createOrderPartnerBuilder.paymentSum += fixDelivery.price.orZero()
            findNavController().navigate(R.id.action_deliveryServiceRegisterStepFourFragment_to_deliveryAddressRegisterStepFourFragment)
        }
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun recycleDeliveryServices(services: List<DeliveryServiceResponse>) = when {
        services.isEmpty() -> {
            showMessage(requireContext(), message = "Not Found")
        }
        services.size == 1 -> addDeliveryService(services.first())
        else -> setChooseDeliveryService(
            services.first(),
            services.last()
        )
    }

    private fun setChooseDeliveryService(
        fasten: DeliveryServiceResponse,
        default: DeliveryServiceResponse
    ) {
        deliverServiceTypeLinearLayout.isVisible = true
        deliveryServiceTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                deliveryFastenRadioButton.id -> {
                    addDeliveryService(fasten)
                }
                deliveryDefaultRadioButton.id -> {
                    addDeliveryService(default)
                }
            }
        }
    }

    private fun addDeliveryService(service: DeliveryServiceResponse) {
        selectedDeliveryService = service
        deliverySectionLinearLayout.isVisible = true
        deliveryTypeLinearLayout.removeAllViews()
        val view = layoutInflater.inflate(R.layout.delivery_type_company, null, false)
        view.deliverTitleTextView.text = service.delivery?.name.orEmpty()
        view.deliveryCostTextView.text = getString(
            R.string.delivery_cost_format,
            service.priceBv.toString(),
            service.price.toString()
        )
        deliveryTypeLinearLayout.addView(view)
    }
}