package com.example.sunlightdesign.ui.screens.wallet.withdraw.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Office
import com.example.sunlightdesign.ui.screens.profile.register.adapters.CustomPopupAdapter
import com.example.sunlightdesign.ui.screens.profile.register.adapters.OfficesRecyclerAdapter
import com.example.sunlightdesign.ui.screens.wallet.WalletViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.choose_office_bottom_sheet.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class ChooseOfficeBottomSheetDialog(
    private val interaction: ChooseOfficeDialogInteraction,
    private val officesList: ArrayList<Office?>,
    private val citiesList: ArrayList<WalletViewModel.ShortenedCity>? = null,
    private val isCityFilter: Boolean = false
) : BottomSheetDialogFragment(), OfficesRecyclerAdapter.OfficeSelector {

    private lateinit var officesAdapter: OfficesRecyclerAdapter
    private var selectedOfficeId: Int? = null
    private lateinit var citiesAdapter: CustomPopupAdapter<WalletViewModel.ShortenedCity>

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
            }
        }
        return bottomSheetDialog
    }

    override fun onStart() {
        super.onStart()
        officesAdapter.clearSelection()
        citiesAdapter.callFiltering("")
        officeDropDownTextView.setText("")
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
        return inflater.inflate(R.layout.choose_office_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        initRecycler()
        if (isCityFilter) {
            initCities()
            officeDropDown.visibility = View.VISIBLE
        }
    }

    private fun initCities() {
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
            officesAdapter.setItems(officesWithCityId)
            Timber.d("cityId: ${c.city_id}")
        }
    }

    override fun onOfficeSelected(id: Int) {
        selectedOfficeId = id
        nextBtn.isEnabled = true
    }

    private fun setListeners() {
        nextBtn.setOnClickListener {
            selectedOfficeId?.let { interaction.onNextBtnPressed(it) }
            dismiss()
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun initRecycler() {
        officesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            officesAdapter = OfficesRecyclerAdapter(requireContext(), this@ChooseOfficeBottomSheetDialog)
            officesAdapter.setItems(officesList)
            adapter = officesAdapter
        }
    }

    interface ChooseOfficeDialogInteraction {
        fun onNextBtnPressed(officeId: Int)
    }
}