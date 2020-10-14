package com.example.sunlightdesign.ui.screens.profile.register

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.AddPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.City
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Country
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Region
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Users
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.ui.screens.profile.register.adapters.CustomPopupAdapter
import com.example.sunlightdesign.utils.*
import kotlinx.android.synthetic.main.fragment_register_partner_step_one.*
import kotlinx.android.synthetic.main.fragment_register_partner_step_one.phone_et
import kotlinx.android.synthetic.main.fragment_register_partner_step_one.progress_bar
import kotlinx.android.synthetic.main.sunlight_login.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


class RegisterFragmentStepOne : StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

    private lateinit var countriesAdapter: CustomPopupAdapter<Country>
    private lateinit var regionsAdapter: CustomPopupAdapter<Region>
    private lateinit var citiesAdapter: CustomPopupAdapter<City>
    private lateinit var usersAdapter: CustomPopupAdapter<Users>

    private var countryId: Int = -1
    private var regionId: Int = -1
    private var cityId: Int = -1
    private var sponsorId: Int? = -1

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
        viewModel.getUsersList()
    }

    private fun setListeners() {
        btn_next_step_one.setOnClickListener {
            if (checkFields()) {
                val fullName = partnerFullNameEditText.text.toString().trim().split(" ")
                val position = when{
                    sponsor_itself_rbtn.isChecked -> Constants.PYRAMID_TOP
                    left_side_rbtn.isChecked -> Constants.PYRAMID_LEFT
                    else -> Constants.PYRAMID_RIGHT
                }
                viewModel.createOrder(
                    AddPartner(
                        first_name = fullName[0],
                        last_name = fullName[1],
                        phone = MaskUtils.unMaskValue(
                            MaskUtils.PHONE_MASK,
                            phone_et.text.toString()
                        ),
                        email = "",
                        country_id = countryId,
                        city_id = cityId,
                        iin = iin_et.text.toString(),
                        register_by = sponsorId,
                        position = position
                    )
                )

                findNavController().navigate(R.id.action_stepOneFragment_to_stepTwoFragment)
            }
        }

        sponsor_name_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                sponsor_itself_rbtn.id -> {
                    sponsorId = -1
                    sponsor_name_tv.isEnabled = false
                    sponsor_name_drop_down.isEndIconCheckable = false
                }
                sponsor_other_rbtn.id -> {
                    sponsorId = null
                    sponsor_name_tv.isEnabled = true
                    sponsor_name_drop_down.isEndIconCheckable = true
                }
            }
        }

        attach_document_btn.setOnClickListener {
            viewModel.onAttachDocument()
        }

        document_back_side_remove_tv.setOnClickListener {
            invalidateBackDocument()
        }

        document_rear_side_remove_tv.setOnClickListener {
            invalidateRearDocument()
        }
    }

    private fun setObservers() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })

            countriesList.observe(viewLifecycleOwner, Observer {
                it.countries?.let { it1 -> setCountriesList(ArrayList(it1)) }
                it.regions?.let { it1 -> setRegionsList(ArrayList(it1)) }
                it.cities?.let { it1 -> setCitiesList(ArrayList(it1)) }
            })

            usersList.observe(viewLifecycleOwner, Observer {
                it.users?.let { it1 -> setUsersList(ArrayList(it1)) }
            })

            rearDocument.observe(viewLifecycleOwner, Observer {
                it?.let { setRearDocument(it) }
            })

            backDocument.observe(viewLifecycleOwner, Observer {
                it?.let { setBackDocument(it) }
            })
        }

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
        country_drop_down_tv.setAdapter(countriesAdapter)
        country_drop_down_tv.setOnItemClickListener { parent, view, position, id ->
            val adapter = country_drop_down_tv.adapter
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
        city_drop_down_tv.threshold = 1
        city_drop_down_tv.setAdapter(citiesAdapter)
        city_drop_down_tv.setOnItemClickListener { parent, view, position, id ->
            val adapter = city_drop_down_tv.adapter
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
        region_drop_down_tv.threshold = 1
        region_drop_down_tv.setAdapter(regionsAdapter)
        region_drop_down_tv.setOnItemClickListener { parent, view, position, id ->
            val adapter = region_drop_down_tv.adapter
            val r = adapter.getItem(position) as Region
            regionId = r.id ?: -1
            citiesAdapter.callFiltering("")
            Timber.d("regionID: $regionId")
        }
    }

    private fun setUsersList(list: ArrayList<Users>) {
        usersAdapter = CustomPopupAdapter(
            context = requireContext(),
            items = list,
            valueChecker = object : CustomPopupAdapter.ValueChecker<Users, String> {
                override fun filter(value: Users, subvalue: String?): Boolean {
                    if (subvalue == null || subvalue.isBlank())
                        return true
                    return value.first_name?.contains(subvalue) ?: false ||
                            value.last_name?.contains(subvalue) ?: false
                }

                override fun toString(value: Users?): String =
                    "${value?.first_name} ${value?.last_name}"

                override fun toLong(value: Users?): Long =
                    value?.id?.toLong() ?: -1

            }
        )
        sponsor_name_tv.threshold = 1
        sponsor_name_tv.setAdapter(usersAdapter)
        sponsor_name_tv.setOnItemClickListener { parent, view, position, id ->
            val adapter = sponsor_name_tv.adapter
            sponsorId = (adapter.getItem(position) as Users).id
            usersAdapter.callFiltering("")
            Timber.d("sponsor: $sponsorId")
        }
    }

    private fun setupMask() {
        MaskImpl(
            MaskUtils.createSlotsFromMask(IIN_MASK, true), true
        ).also {
            it.isHideHardcodedHead = false
            MaskFormatWatcher(it).apply {
                installOn(iin_et)
                onTextFormatted {
                    if (isIinValid(iin_et.text.toString())) phone_et.requestFocus()
                }
            }
        }

        MaskImpl(
            MaskUtils.createSlotsFromMask(MaskUtils.PHONE_MASK, true), true
        ).also {
            it.isHideHardcodedHead = true
            MaskFormatWatcher(it).apply {
                installOn(phone_et)
                onTextFormatted { updateSignUpBtn() }
            }
        }
    }

    private fun setRearDocument(uri: Uri) {
        document_rear_side_card.visibility = View.VISIBLE
        document_rear_side_iv.invalidate()
        document_rear_side_iv.setImageURI(uri)
        document_rear_side_name_tv.text = getFileName(requireContext(), uri)
        document_rear_side_size_tv.text = getFileSizeInLong(requireContext(), uri).toString()
    }

    private fun invalidateRearDocument() {
        document_rear_side_card.visibility = View.GONE
        document_rear_side_iv.invalidate()
        viewModel.onRearDocumentInvalidate()
    }

    private fun setBackDocument(uri: Uri) {
        document_back_side_card.visibility = View.VISIBLE
        document_back_side_iv.invalidate()
        document_back_side_iv.setImageURI(uri)
        document_back_side_name_tv.text = getFileName(requireContext(), uri)
        document_back_side_size_tv.text = getFileSizeInLong(requireContext(), uri).toString()
    }

    private fun invalidateBackDocument() {
        document_back_side_card.visibility = View.GONE
        document_back_side_iv.invalidate()
        viewModel.onBackDocumentInvalidate()
    }

    private fun updateSignUpBtn() {
        btn_next_step_one.isEnabled =
            if (isPhoneValid(phone_et) && isIinValid(iin_et.text.toString())) {
                activity?.closeKeyboard()
                true
            } else false
    }

    private fun checkFields(): Boolean {
        return partnerFullNameEditText.text.toString().isNotBlank() && sponsorId != null &&
                partnerFullNameEditText.text.toString().trim().split(" ").size > 1
    }

}
