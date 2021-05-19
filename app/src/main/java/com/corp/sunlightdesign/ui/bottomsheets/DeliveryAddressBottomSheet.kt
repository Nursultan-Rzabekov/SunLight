package com.corp.sunlightdesign.ui.bottomsheets

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliveryServiceResponse
import com.corp.sunlightdesign.utils.showMessage
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.page_delivery_address_detailed.*

class DeliveryAddressBottomSheet(
    private val interaction: Interaction
): BottomSheetDialogFragment() {

    private var cityId: Int = -1
    private var countryId: Int = -1
    private var regionId: Int = -1
    private var countryCode: String = ""

    private var selectedDeliveryService: DeliveryServiceResponse? = null

    companion object {
        const val TAG = "ModalBottomSheet address"
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        nullifyAll()
    }

    private fun nullifyAll() {
        streetAddressEditText.setText("")
        partnerEditText.setText("")
        nullifyDelivery()
    }

    private fun nullifyDelivery() {
        countryId = -1
        regionId = -1
        cityId = -1
        countryCode = ""
        selectedDeliveryService = null
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
        return inflater.inflate(R.layout.page_delivery_address_detailed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
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
                message = "Delivery not found"
            )

            interaction.onDeliveryServiceAddressSelected(
                address = streetAddressEditText.text.toString(),
                partner = partnerEditText.text.toString(),
                cityId = cityId,
                countryId = countryId,
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

    private fun checkFields(): Boolean {
        return !streetAddressEditText.text.isNullOrBlank()
                && !partnerEditText.text.isNullOrBlank()
    }

    fun setDeliveryService(
        cityId: Int,
        countryId: Int,
        countryCode: String,
        regionId: Int,
        deliveryService: DeliveryServiceResponse
    ) {
        this.cityId = cityId
        this.countryId = countryId
        this.regionId = regionId
        this.countryCode = countryCode
        this.selectedDeliveryService = deliveryService
    }

    interface Interaction {
        fun onDeliveryServiceAddressSelected(
            address: String,
            partner: String,
            cityId: Int,
            countryId: Int,
            countryCode: String,
            regionId: Int,
            deliveryService: DeliveryServiceResponse
        )
    }
}