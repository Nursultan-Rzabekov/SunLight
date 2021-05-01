package com.example.sunlightdesign.ui.bottomsheets

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.City
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Country
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Region
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliveryService
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliveryServiceResponse
import com.example.sunlightdesign.utils.BasePopUpAdapter
import com.example.sunlightdesign.utils.showDialog
import com.example.sunlightdesign.utils.showListPopupWindow
import com.example.sunlightdesign.utils.showMessage
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.delivery_type_company.view.*
import kotlinx.android.synthetic.main.page_delivery_address_main.*
import java.util.*
import kotlin.collections.ArrayList

class DeliveryServiceBottomSheet(
    private val interaction: Interaction,
    private var countriesList: CountriesList = CountriesList(null, null, null)
): BottomSheetDialogFragment() {

    private val countriesPopUpAdapter = BasePopUpAdapter<Country>()
    private val regionsPopUpAdapter = BasePopUpAdapter<Region>()
    private val citiesPopUpAdapter = BasePopUpAdapter<City>()

    private var countryId: Int = -1
    private var regionId: Int = -1
    private var cityId: Int = -1
    private var countryCode: String = ""

    private var selectedDeliveryService: DeliveryServiceResponse? = null

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        bottomSheetDialog.setOnShowListener {
            val bottomSheet = it as BottomSheetDialog
            val parentLayout =
                bottomSheet.findViewById<View>(R.id.design_bottom_sheet)
            parentLayout?.let { layout ->
                val behaviour = BottomSheetBehavior.from(layout)
                setupFullHeight(layout)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.isHideable = false
            }
        }
        return bottomSheetDialog
    }

    fun setLocations(countriesList: CountriesList) {
        this.countriesList = countriesList
        context?.let { setupLists() }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_delivery_address_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        nullifyAll()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        nullifyAll()
    }

    private fun nullifyAll() {
        countryTextView.text = ""
        regionsTextView.text = ""
        citiesTextView.text = ""
        countryId = -1
        regionId = -1
        cityId = -1
        nullifyDelivery()
        setupLists()
    }

    private fun nullifyDelivery() {
        selectedDeliveryService = null
        deliveryTypeLinearLayout.removeAllViews()
        deliverySectionLinearLayout.isVisible = false
        deliverServiceTypeLinearLayout.isVisible = false
    }

    private fun setupListeners() {
        nextButton.setOnClickListener {
            if (!checkFields()) return@setOnClickListener showMessage(
                requireContext(),
                message = getString(R.string.fill_all_the_field)
            )

            val fixDelivery = selectedDeliveryService
            fixDelivery ?: return@setOnClickListener showMessage(
                requireContext(),
                message = "Delivery Service is not found"
            )

            interaction.onDeliveryServiceSelected(
                countryId = countryId,
                cityId = cityId,
                regionId = regionId,
                deliveryService = fixDelivery,
                countryCode = countryCode
            )
            dismiss()
        }
        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun setupLists() {
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
        interaction.onDeliveryServiceAddressChosen(
            countryId = countryId,
            regionId = regionId,
            cityId = cityId,
            countryCode = countryCode
        )
    }

    fun recycleDeliveryServices(services: List<DeliveryServiceResponse>) {
        nullifyDelivery()
        when {
            services.isEmpty() -> {
                showMessage(requireContext(), message = "Not Found")
            }
            services.size == 1 -> addDeliveryService(services.first())
            else -> setChooseDeliveryService(
                services.first(),
                services.last()
            )
        }
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

    interface Interaction {

        fun onDeliveryServiceSelected(
            cityId: Int,
            countryId: Int,
            regionId: Int,
            deliveryService: DeliveryServiceResponse,
            countryCode: String
        )

        fun onDeliveryServiceAddressChosen(
            countryId: Int,
            regionId: Int,
            cityId: Int,
            countryCode: String
        )
    }
}