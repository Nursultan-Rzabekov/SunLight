package com.example.sunlightdesign.ui.screens.order.sheetDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.City
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Country
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Region
import com.example.sunlightdesign.ui.screens.profile.register.adapters.CustomPopupAdapter
import com.example.sunlightdesign.utils.showDialog
import com.example.sunlightdesign.utils.showMessage
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.address_fields_bottom_sheet.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class AddressFieldsBottomSheet(
    private val interaction: Interaction,
    private var countriesList: CountriesList = emptyCountries
) : BottomSheetDialogFragment() {

    private lateinit var countriesAdapter: CustomPopupAdapter<Country>
    private lateinit var regionsAdapter: CustomPopupAdapter<Region>
    private lateinit var citiesAdapter: CustomPopupAdapter<City>

    private var countryId: Int = -1
    private var regionId: Int = -1
    private var cityId: Int = -1

    companion object {
        const val TAG = "address"
        val emptyCountries = CountriesList(null, null, null)
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
            }
        }
        return bottomSheetDialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.address_fields_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        loadLocations()
    }

    private fun loadLocations() {
        setCountriesList(ArrayList(countriesList.countries ?: listOf()))
        setRegionsList(ArrayList(countriesList.regions ?: listOf()))
        setCitiesList(ArrayList(countriesList.cities ?: listOf()))
    }

    override fun onResume() {
        super.onResume()
        countryDropDownTextView.setText("")
        regionDropDownTextView.setText("")
        cityDropDownTextView.setText("")
        countriesAdapter.callFiltering("")
        regionsAdapter.callFiltering("")
        citiesAdapter.callFiltering("")
        loadLocations()
    }

    private fun setListeners() {
        nextBtn.setOnClickListener {
            if (!checkFields()) return@setOnClickListener showMessage(
                requireContext(), message = getString(R.string.fill_all_the_field))

            interaction.onAddressPassed(
                countryId, regionId, cityId, streetAddressEditText.text.toString().trim())
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    fun setLocations(countriesList: CountriesList) {
        this.countriesList = countriesList
        context?.let { loadLocations() }
    }

    private fun setCountriesList(list: ArrayList<Country>) {
        countriesAdapter = CustomPopupAdapter(
            context = requireContext(),
            items = list,
            valueChecker = object : CustomPopupAdapter.ValueChecker<Country, String> {
                override fun filter(value: Country, subvalue: String?): Boolean {
                    val v = value.country_name.toString()
                    if (subvalue == null || subvalue.isBlank())
                        return true
                    return v.toLowerCase(Locale.getDefault()).startsWith(subvalue)
                }

                override fun toString(value: Country?): String = value?.country_name.toString()

                override fun toLong(value: Country?): Long = value?.id?.toLong() ?: -1

            }
        )
        countryDropDownTextView.setAdapter(countriesAdapter)
        countryDropDownTextView.setOnItemClickListener { parent, view, position, id ->
            val adapter = countryDropDownTextView.adapter
            val c = adapter.getItem(position) as Country
            countryId = c.id ?: -1
            regionsAdapter.callFiltering("")
            Timber.d("countryID: $countryId")
        }
    }

    private fun setCitiesList(list: ArrayList<City>) {
        citiesAdapter = CustomPopupAdapter(
            context = requireContext(),
            items = list,
            valueChecker = object : CustomPopupAdapter.ValueChecker<City, String> {
                override fun filter(value: City, subvalue: String?): Boolean {
                    val v = value.city_name.toString()
                    if (subvalue == null || subvalue.isBlank())
                        return (regionId == value.region_id || regionId == -1)
                    return v.toLowerCase(Locale.getDefault()).startsWith(subvalue) &&
                            (regionId == value.region_id || regionId == -1)
                }

                override fun toString(value: City?): String {
                    return value?.city_name.toString()
                }

                override fun toLong(value: City?): Long {
                    return value?.id?.toLong() ?: -1
                }

            }
        )
        cityDropDownTextView.threshold = 1
        cityDropDownTextView.setAdapter(citiesAdapter)
        cityDropDownTextView.setOnItemClickListener { parent, view, position, id ->
            val adapter = cityDropDownTextView.adapter
            val c = adapter.getItem(position) as City
            cityId = c.id ?: -1
            Timber.d("cityID: $cityId")
        }
    }

    private fun setRegionsList(list: ArrayList<Region>) {
        regionsAdapter = CustomPopupAdapter(
            context = requireContext(),
            items = list,
            valueChecker = object : CustomPopupAdapter.ValueChecker<Region, String> {
                override fun filter(value: Region, subvalue: String?): Boolean {
                    Timber.d("countryID: $countryId")
                    val v = value.region_name.toString()
                    if (subvalue == null || subvalue.isBlank())
                        return (countryId == value.country_id || countryId == -1)
                    return v.toLowerCase(Locale.getDefault()).startsWith(subvalue) &&
                            (countryId == value.country_id || countryId == -1)
                }

                override fun toString(value: Region?): String {
                    return value?.region_name.toString()
                }

                override fun toLong(value: Region?): Long {
                    return value?.id?.toLong() ?: -1
                }

            }
        )
        regionDropDownTextView.threshold = 1
        regionDropDownTextView.setAdapter(regionsAdapter)
        regionDropDownTextView.setOnItemClickListener { parent, view, position, id ->
            val adapter = regionDropDownTextView.adapter
            val r = adapter.getItem(position) as Region
            regionId = r.id ?: -1
            citiesAdapter.callFiltering("")
            Timber.d("regionID: $regionId")
        }
    }

    private fun checkFields(): Boolean {
        return !countryDropDownTextView.text.isNullOrBlank() &&
                !cityDropDownTextView.text.isNullOrBlank() &&
                !regionDropDownTextView.text.isNullOrBlank() &&
                !streetAddressEditText.text.isNullOrBlank()
    }

    interface Interaction {
        fun onAddressPassed(country: Int, region: Int, city: Int, address: String)
    }
}