package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListPopupWindow
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


class RegisterFragmentStepOne : StrongFragment<ProfileViewModel>(ProfileViewModel::class) {


    private lateinit var customAdapter: CustomPopupAdapter<*>
    private var countriesArrayList = arrayListOf<Country>()
    private var citiesArrayList = arrayListOf<City>()
    private var regionsArrayList = arrayListOf<Region>()
    private val usersArrayList = arrayListOf<Users>()


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
        //viewModel.getUsersList()

    }

    private fun setListeners(){
        btn_next_step_one.setOnClickListener {
            findNavController().navigate(R.id.action_stepOneFragment_to_stepTwoFragment)
        }

        setCountriesList()

        region_drop_down.setEndIconOnClickListener {
            showRegionsList(it)
        }

        city_drop_down.setEndIconOnClickListener {
            showCitiesList(it)
        }

    }

    private fun setObservers(){
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if(it) View.VISIBLE else View.GONE
            })

            countriesList.observe(viewLifecycleOwner, Observer {
                it.countries?.let { it1 -> countriesArrayList = ArrayList(it1) }
                it.regions?.let { it1 -> regionsArrayList = ArrayList(it1) }
                it.cities?.let { it1 -> citiesArrayList = ArrayList(it1) }
            })

            usersList.observe(viewLifecycleOwner, Observer {
                it.users?.let { it1 -> usersArrayList.addAll(it1) }
            })
        }

    }

    private fun setCountriesList(){
        customAdapter = CustomPopupAdapter<Country>(requireContext(), countriesArrayList)
        country_drop_down_tv.setAdapter(customAdapter)
    }

    private fun showCitiesList(view: View){
        val listPopupWindow = ListPopupWindow(requireContext())
        customAdapter = CustomPopupAdapter(requireContext(), citiesArrayList)
//        listPopupWindow.setAdapter(customAdapter)
//        listPopupWindow.anchorView = view
//        listPopupWindow.setOnItemClickListener { _, _, i, _ ->
//            city_drop_down_tv.setText(citiesArrayList[i].city_name)
//            listPopupWindow.dismiss()
//        }
//        listPopupWindow.show()
    }

    private fun showRegionsList(view: View){
        val listPopupWindow = ListPopupWindow(requireContext())
        customAdapter = CustomPopupAdapter(requireContext(), citiesArrayList)
//        listPopupWindow.setAdapter(customAdapter)
//        listPopupWindow.anchorView = view
//        listPopupWindow.setOnItemClickListener { _, _, i, _ ->
//            region_drop_down_tv.setText(regionsArrayList[i].region_name)
//            listPopupWindow.dismiss()
//        }
//        listPopupWindow.show()
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
