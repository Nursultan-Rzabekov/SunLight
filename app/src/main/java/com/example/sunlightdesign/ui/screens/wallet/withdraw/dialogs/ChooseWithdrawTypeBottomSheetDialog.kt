package com.example.sunlightdesign.ui.screens.wallet.withdraw.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sunlightdesign.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.payment_bottom_sheet.*

class ChooseWithdrawTypeBottomSheetDialog(
    private val interaction: ChooseWithdrawTypeInteraction
): BottomSheetDialogFragment()  {

    companion object{
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
        return inflater.inflate(R.layout.payment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withCashTextView.setOnClickListener {
            interaction.onWithdrawTypeCashSelected()
            dismiss()
        }

        withCardTextView.setOnClickListener {
            interaction.onWithdrawTypeCardSelected()
            dismiss()
        }
    }

    interface ChooseWithdrawTypeInteraction{
        fun onWithdrawTypeCashSelected()
        fun onWithdrawTypeCardSelected()
    }
}