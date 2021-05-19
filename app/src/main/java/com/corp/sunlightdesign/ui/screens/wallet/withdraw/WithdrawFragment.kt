package com.corp.sunlightdesign.ui.screens.wallet.withdraw

import android.app.Dialog
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.User
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.VerifyUser
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyX
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.WithdrawalReceipt
import com.corp.sunlightdesign.ui.base.StrongFragment
import com.corp.sunlightdesign.ui.screens.profile.register.adapters.CustomPopupAdapter
import com.corp.sunlightdesign.ui.screens.wallet.WalletViewModel
import com.corp.sunlightdesign.ui.screens.wallet.WalletViewModel.ShortenedCity
import com.corp.sunlightdesign.ui.screens.wallet.withdraw.dialogs.ChooseOfficeBottomSheetDialog
import com.corp.sunlightdesign.ui.screens.wallet.withdraw.dialogs.ChooseWithdrawTypeBottomSheetDialog
import com.corp.sunlightdesign.ui.screens.wallet.withdraw.dialogs.WithdrawSuccessBottomSheetDialog
import com.corp.sunlightdesign.usecase.usercase.walletUse.post.SetWithdrawal
import com.corp.sunlightdesign.utils.showMessage
import kotlinx.android.synthetic.main.bv_payment_card_layout.*
import kotlinx.android.synthetic.main.dialog_notify.*
import kotlinx.android.synthetic.main.fragment_wallet.progress_bar
import kotlinx.android.synthetic.main.toolbar_with_back.*
import kotlinx.android.synthetic.main.transfer_card_layout.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class WithdrawFragment :
    StrongFragment<WalletViewModel>(WalletViewModel::class),
    ChooseWithdrawTypeBottomSheetDialog.ChooseWithdrawTypeInteraction,
    ChooseOfficeBottomSheetDialog.ChooseOfficeDialogInteraction,
    WithdrawSuccessBottomSheetDialog.WithdrawSuccessDialogInteraction {

    private lateinit var currencyAdapter: CustomPopupAdapter<CurrencyX>
    private val chooseWithdrawTypeBottomSheetDialog: ChooseWithdrawTypeBottomSheetDialog by lazy {
        ChooseWithdrawTypeBottomSheetDialog(this)
    }
    private lateinit var chooseOfficeBottomSheetDialog: ChooseOfficeBottomSheetDialog
    private lateinit var withdrawSuccessBottomSheetDialog: WithdrawSuccessBottomSheetDialog

    companion object {
        const val WITHDRAW_TYPE_WITH_CASH = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_withdraw, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        incomeAndOutcomeLayout.isVisible = false

        setListeners()
        configViewModel()

        backBtn.setText(R.string.back)
        titleTextView.setText(R.string.withdraw_money)

        viewModel.getCurrencyInfo()
        viewModel.getOffices()
        viewModel.getWalletInfo()
    }

    private fun configViewModel(){
        viewModel.apply {

            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if(it) View.VISIBLE else View.GONE
            })

            calculateInfo.observe(viewLifecycleOwner, Observer {
                it.currencies?.let { currencyList -> setCurrencies(ArrayList(currencyList)) }
                it.user?.let { user -> bindWalletInfo(user) }
            })

            selectedCurrency.observe(viewLifecycleOwner, Observer {
                bvToCurrencyTextView.text = getString(
                    R.string.bv_to_currency,
                    it.currency_bv_value.toString(),
                    it.currency_code.toString()
                )
                amountConvertedTextView.text = calculateAmount(it).toString()
                currencySymbolTextView.text = it.currency_sign
                finalAmountTextView.text = ("${calculateAmount(it)} ${it.currency_sign}")
            })

            officesList.observe(viewLifecycleOwner, Observer {
                it.offices?.let { offices ->
                    val citiesList = ArrayList<ShortenedCity>()
                    offices.forEach { office ->
                        office?.city?.id ?: return@forEach
                        citiesList.add(
                            ShortenedCity(
                                office.city.id,
                                office.city.city_name.toString()
                            )
                        )
                    }
                    chooseOfficeBottomSheetDialog = ChooseOfficeBottomSheetDialog(
                        this@WithdrawFragment, ArrayList(offices), citiesList, true)
                }
            })

            walletLiveData.observe(viewLifecycleOwner, Observer {
                bv_balance_amount_tv.text = getString(R.string.amount_bv, it.wallet.main_wallet)
            })

            withdrawReceipt.observe(viewLifecycleOwner, Observer {
                showWithdrawSuccess(it)
            })
        }
    }

    private fun setListeners() {
        withdrawMoneyBtn.setOnClickListener {
            showWithdrawTypeDialog()
        }

        convertBtn.setOnClickListener {
            viewModel.selectedCurrency.value?.let {
                viewModel.onSelectCurrency(it)
            }
            finalAmountLayout.isVisible = true
        }

        backBtn.setOnClickListener {
            activity?.finish()
        }

        amountEditText.addTextChangedListener {
            val convertedAmount = it.toString().toDoubleOrNull() ?: return@addTextChangedListener
            val mainWallet = viewModel.walletLiveData.value?.wallet?.main_wallet
                ?: return@addTextChangedListener

            convertBtn.isEnabled = convertedAmount <= mainWallet
            withdrawMoneyBtn.isEnabled = convertedAmount <= mainWallet
        }
    }

    private fun setCurrencies(currencies: ArrayList<CurrencyX>) {
        currencyAdapter = CustomPopupAdapter(
            context = requireContext(),
            items = currencies,
            valueChecker = object : CustomPopupAdapter.ValueChecker<CurrencyX, String>{
                override fun filter(value: CurrencyX, subvalue: String?): Boolean {
                    val v = value.currency_name.toString()
                    if (subvalue == null || subvalue.isBlank())
                        return true
                    return v.toLowerCase(Locale.getDefault()).startsWith(subvalue)
                }

                override fun toString(value: CurrencyX?): String = value?.currency_code.toString()

                override fun toLong(value: CurrencyX?): Long = value?.id?.toLong() ?: -1

            }
        )
        currency_tv.setAdapter(currencyAdapter)
        currency_tv.setOnItemClickListener { parent, view, position, id ->
            val adapter = currency_tv.adapter
            val currency = adapter.getItem(position) as CurrencyX

            viewModel.onSelectCurrency(currency)

            currencyAdapter.callFiltering("")
            Timber.d("currency: $currency")
        }
        if (currencies.isNotEmpty()) {
            currency_tv.selectItem(0)
            viewModel.onSelectCurrency(currencies.first())
        }
    }

    private fun bindWalletInfo(user: User) {
        bv_balance_amount_tv.text = getString(R.string.amount_bv, user.wallet?.main_wallet)
    }

    override fun onWithdrawTypeCashSelected() {
        showOfficeChoiceDialog()
    }

    override fun onWithdrawTypeCardSelected() {
        when (viewModel.walletLiveData.value?.user?.verifyuser?.status) {
            VerifyUser.STATUS_WAITING_VERIFICATION -> {
                showVerificationWaitErrorDialog(getString(R.string.error_waiting_verification))
            }
            VerifyUser.STATUS_NOT_VERIFIED, VerifyUser.STATUS_REJECTED -> {
                showNotVerifiedErrorDialog(getString(R.string.error_need_verification))
            }
            else -> {
                //FIXME when its allowed
            }
        }
    }

    override fun onNextBtnPressed(officeId: Int) {
        val bvValue = amountEditText.text.toString().toInt()
        val amount = amountConvertedTextView.text.toString().toDouble()
        val currencyId = viewModel.selectedCurrency.value?.id ?: return showErrorDialog("CurrencyId")
        val currencyValue = viewModel.selectedCurrency.value?.currency_bv_value ?: return showErrorDialog("CurrencyValue")
        val userId = viewModel.calculateInfo.value?.user?.id ?: return showErrorDialog("UserId")

        viewModel.storeWithdraw(
            SetWithdrawal(
                bvValue = bvValue,
                cashAmount = amount,
                currencyId = currencyId,
                currencyValue = currencyValue,
                officeId = officeId,
                userId = userId,
                withdrawType = WITHDRAW_TYPE_WITH_CASH
            )
        )
    }

    override fun onClose() {
        // finish activity
    }

    private fun showWithdrawTypeDialog() {
        chooseWithdrawTypeBottomSheetDialog.show(
            parentFragmentManager,
            ChooseWithdrawTypeBottomSheetDialog.TAG
        )
    }

    private fun showOfficeChoiceDialog() {
        chooseOfficeBottomSheetDialog.show(
            parentFragmentManager,
            ChooseOfficeBottomSheetDialog.TAG
        )
    }

    private fun showWithdrawSuccess(WithdrawalReceipt: WithdrawalReceipt) {
        withdrawSuccessBottomSheetDialog = WithdrawSuccessBottomSheetDialog(
            WithdrawalReceipt,
            this
        )
        withdrawSuccessBottomSheetDialog.show(
            parentFragmentManager,
            WithdrawSuccessBottomSheetDialog.TAG
        )
    }

    private fun showErrorDialog(message: String) {
        showMessage(requireContext(), message = message)
    }

    private fun showVerificationWaitErrorDialog(message: String) {
        val dialog = Dialog(requireContext(), R.style.FullDialog).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.color.transparent))
            setCancelable(false)
            setContentView(R.layout.dialog_notify)
        }
        dialog.notify_desc_tv.text = message
        dialog.notify_icon_iv.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_failed))
        dialog.notify_title_tv.text = getString(R.string.oops_message)

        dialog.notify_ok_btn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showNotVerifiedErrorDialog(message: String) {
        val dialog = Dialog(requireContext(), R.style.FullDialog).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.color.transparent))
            setCancelable(false)
            setContentView(R.layout.dialog_notify)
        }
        dialog.notify_desc_tv.makeLink(message, getString(R.string.error_need_verification_phrase)) {
            findNavController().navigate(R.id.action_withdrawFragment_to_editPartnerActivity)
            viewModel.withActivity { it.finish() }
        }
        dialog.notify_icon_iv.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_failed))
        dialog.notify_title_tv.text = getString(R.string.oops_message)

        dialog.notify_ok_btn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun calculateAmount(currency: CurrencyX): Double {
        val amountInBv = amountEditText.text.toString().toDoubleOrNull() ?: return .0

        return currency fromCurrencyToConvertedAmount amountInBv
    }

    private infix fun CurrencyX.fromCurrencyToConvertedAmount(amount: Double): Double {
        if (this.currency_bv_value == null) return .0

        return amount * this.currency_bv_value
    }

    private fun AutoCompleteTextView.selectItem(position: Int) {
        this.setText((this.adapter.getItem(position) as CurrencyX).toString(), false)
    }

    private fun TextView.makeLink(
        text: String,
        phrase: String,
        listener: () -> Unit
    ) {
        val spannableString = SpannableString(text)
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.color = ContextCompat.getColor(context, R.color.colorPrimary)
                ds.isUnderlineText = true
            }
            override fun onClick(view: View) {
                listener()
            }
        }
        val start = text.indexOf(phrase)
        val end = start + phrase.length

        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        movementMethod = LinkMovementMethod.getInstance()
        setText(spannableString, TextView.BufferType.SPANNABLE)
    }
}