package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.utils.*
import kotlinx.android.synthetic.main.fragment_register_partner_step_one.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


class RegisterFragmentStepOne : StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

    private lateinit var countriesAdapter: CustomPopupAdapter<Country>
    private lateinit var regionsAdapter: CustomPopupAdapter<Region>
    private val usersArrayList = arrayListOf<Users>()

    private var countryId: Int = -1
    private var regionId: Int = -1
    private var cityId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_partner_step_one, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()
        setupMask()
        setObservers()

        viewModel.getCountriesList()
//        viewModel.getUsersList()
    }

    private fun setListeners(){
        btn_next_step_one.setOnClickListener {
            findNavController().navigate(R.id.action_stepOneFragment_to_stepTwoFragment)
        }
    }

    private fun setObservers(){
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if(it) View.VISIBLE else View.GONE
            })

            countriesList.observe(viewLifecycleOwner, Observer {
                it.countries?.let { it1 -> setCountriesList(ArrayList(it1)) }
                it.regions?.let { it1 -> setRegionsList(ArrayList(it1)) }
                it.cities?.let { it1 -> setCitiesList(ArrayList(it1)) }
            })

            usersList.observe(viewLifecycleOwner, Observer {
                it.users?.let { it1 -> usersArrayList.addAll(it1) }
            })
        }

    }

    private fun setCountriesList(list: ArrayList<Country>){
        countriesAdapter = CustomPopupAdapter(
            context = requireContext(),
            items = list,
            valueChecker = object: CustomPopupAdapter.ValueChecker<Country, String> {
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
        country_drop_down_tv.setAdapter(countriesAdapter)
        country_drop_down_tv.setOnItemClickListener { parent, view, position, id ->
            val adapter = country_drop_down_tv.adapter
            val c = adapter.getItem(position) as Country
            countryId = c.id ?: -1
            regionsAdapter.callFiltering("")
            Timber.d("countryID: $countryId")
        }
    }

    private fun setCitiesList(list: ArrayList<City>){
        val customCitiesAdapter = CustomPopupAdapter(
            context = requireContext(),
            items = list,
            valueChecker = object: CustomPopupAdapter.ValueChecker<City, String> {
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
        city_drop_down_tv.threshold = 1
        city_drop_down_tv.setAdapter(customCitiesAdapter)
    }

    private fun setRegionsList(list: ArrayList<Region>){
        regionsAdapter = CustomPopupAdapter(
            context = requireContext(),
            items = list,
            valueChecker = object: CustomPopupAdapter.ValueChecker<Region, String> {
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
        region_drop_down_tv.threshold = 1
        region_drop_down_tv.setAdapter(regionsAdapter)
    }

    private fun setupMask() {
        MaskImpl(
            MaskUtils.createSlotsFromMask(IIN_MASK, true), true).also {
            it.isHideHardcodedHead = false
            MaskFormatWatcher(it).apply {
                installOn(iin_et)
                onTextFormatted {
                    if (isIinValid(iin_et.text.toString())) phone_et.requestFocus()
                }
            }
        }

        MaskImpl(
            MaskUtils.createSlotsFromMask(MaskUtils.PHONE_MASK, true), true).also {
            it.isHideHardcodedHead = true
            MaskFormatWatcher(it).apply {
                installOn(phone_et)
                onTextFormatted { updateSignUpBtn() }
            }
        }
    }

    private fun updateSignUpBtn() {
        btn_next_step_one.isEnabled = if (isPhoneValid(phone_et) && isIinValid(iin_et.text.toString())) {
            activity?.closeKeyboard()
            true
        } else false
    }

}
