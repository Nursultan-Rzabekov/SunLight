package com.example.sunlightdesign.ui.screens.wallet.withdraw.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.WithdrawalReceipt
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.chosen_office_bottom_sheet.*
import kotlinx.android.synthetic.main.office_departments_item.*

class WithdrawSuccessBottomSheetDialog(
    private val withdrawReceipt: WithdrawalReceipt,
    private val interaction: WithdrawSuccessDialogInteraction
): BottomSheetDialogFragment() {

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chosen_office_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindInfo()
        setListeners()
    }

    private fun setListeners() {
        okBtn.setOnClickListener {
            interaction.onClose()
            dismiss()
        }
    }

    private fun bindInfo() {
        successInfoTextView.text = withdrawReceipt.message
        office_name_tv.text = withdrawReceipt.office.office_name
        office_phonenumber_tv.text = withdrawReceipt.office.phone
        office_address_tv.text = withdrawReceipt.office.address
        office_time_tv.text = withdrawReceipt.office.close_hours

        Glide.with(this)
            .load(withdrawReceipt.office.office_image_path)
            .placeholder(R.drawable.rectangle_40)
            .error(R.drawable.rectangle_40)
            .into(office_image_iv)
    }

    interface WithdrawSuccessDialogInteraction {
        fun onClose()
    }
}