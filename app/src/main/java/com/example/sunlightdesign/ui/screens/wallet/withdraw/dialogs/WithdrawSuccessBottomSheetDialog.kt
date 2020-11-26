package com.example.sunlightdesign.ui.screens.wallet.withdraw.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.WithdrawalReceipt
import com.example.sunlightdesign.utils.getImageUrl
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
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
            .load(getImageUrl(withdrawReceipt.office.office_image_path))
            .into(office_image_iv)
    }

    interface WithdrawSuccessDialogInteraction {
        fun onClose()
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
}