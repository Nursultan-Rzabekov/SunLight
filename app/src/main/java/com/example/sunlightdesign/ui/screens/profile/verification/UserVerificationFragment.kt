package com.example.sunlightdesign.ui.screens.profile.verification

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Users
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.register.adapters.CustomPopupAdapter
import com.example.sunlightdesign.utils.*
import kotlinx.android.synthetic.main.document_layout_item.view.*
import kotlinx.android.synthetic.main.fragment_register_partner_step_one.*
import kotlinx.android.synthetic.main.fragment_verify_user.*
import kotlinx.android.synthetic.main.toolbar_with_back.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class UserVerificationFragment:
    StrongFragment<UserVerificationViewModel>(UserVerificationViewModel::class) {

    private lateinit var banksAdapter: CustomPopupAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_verify_user, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        backBtn.setText(R.string.back)
        titleTextView.setText(R.string.verification)

        setListeners()
        setupMask()
        entepreneurChecked()

        setupBankList(listOf("asd", "redf"))

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.apply {
            frontDocument.observe(viewLifecycleOwner, Observer {
                it ?: return@Observer
                addDocumentView(it) {
                    onFrontDocumentInvalidate()
                }
            })

            backDocument.observe(viewLifecycleOwner, Observer {
                it ?: return@Observer
                addDocumentView(it) {
                    onBackDocumentInvalidate()
                }
            })

            contractDocument.observe(viewLifecycleOwner, Observer {
                it ?: return@Observer
                addDocumentView(it) {
                    onContractDocumentInvalidate()
                }
            })
        }
    }

    private fun setListeners() {
        backBtn.setOnClickListener {
            viewModel.withActivity { it.finish() }
        }

        userOccupationRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                physRadioBtn.id -> entepreneurChecked()
                else -> legalChecked()
            }
        }

        attachDocumentBtn.setOnClickListener {
            viewModel.onAttachDocument()
        }
    }

    private fun setupMask() {
        MaskImpl(
            MaskUtils.createSlotsFromMask(IIN_MASK, true), true
        ).also {
            it.isHideHardcodedHead = false
            MaskFormatWatcher(it).apply {
                installOn(iinEditText)
            }
        }
    }

    private fun setupBankList(items: List<String>) {
        banksAdapter = CustomPopupAdapter(
            context = requireContext(),
            items = ArrayList(items),
            valueChecker = object: CustomPopupAdapter.ValueChecker<String, String> {
                override fun filter(value: String, subvalue: String?): Boolean {
                    val v = value
                    if (subvalue == null || subvalue.isBlank())
                        return true
                    return v.toLowerCase(Locale.getDefault()).startsWith(subvalue)
                }

                override fun toString(value: String?): String = value.toString()

                override fun toLong(value: String?): Long = value?.length?.toLong() ?: -1

            }
        )
        bankDropDownText.threshold = 1
        bankDropDownText.setAdapter(banksAdapter)
        bankDropDownText.setOnItemClickListener { parent, view, position, id ->
            val adapter = sponsor_name_tv.adapter
            Timber.d("sponsor: ${adapter.getItem(position)}")
        }
    }

    private fun entepreneurChecked() {
        companyTitleLabelTextView.visibility = View.GONE
        companyTitleLayout.visibility = View.GONE
    }

    private fun legalChecked() {
        companyTitleLabelTextView.visibility = View.VISIBLE
        companyTitleLayout.visibility = View.VISIBLE
    }

    private inline fun addDocumentView(uri: Uri, crossinline invalidate: () -> Unit) {
        val view = layoutInflater.inflate(R.layout.document_layout_item, null)
        view.documentNameTextView.text = getFileName(requireContext(), uri)
        view.documentSizeTextView.text = getFileSizeInLong(requireContext(), uri).toString()
        view.documentImageView.setImageURI(uri)
        view.documentRemoveTextView.setOnClickListener {
            documentsContainer.removeView(view)
            invalidate()
            checkDocumentsCapacity()
        }

        documentsContainer.addView(view)
        checkDocumentsCapacity()
    }

    private fun checkDocumentsCapacity() {
        attachDocumentBtn.isEnabled = documentsContainer.childCount != 3
    }

    private fun checkFields(): Boolean {

        val message = when {
            firstNameEditText.text.toString().isBlank() ->
                "${getString(R.string.fill_the_field)} ${getString(R.string.first_name)}"

            lastNameEditText.text.toString().isBlank() ->
                "${getString(R.string.fill_the_field)} ${getString(R.string.last_name)}"

            middleNameEditText.text.toString().isBlank() ->
                "${getString(R.string.fill_the_field)} ${getString(R.string.middle_name)}"

            !isIinValid(iinEditText.text.toString()) ->
                "${getString(R.string.fill_the_field)} ${getString(R.string.iin)}"

            viewModel.backDocument.value == null ->
                "${getString(R.string.attach_file)}(${getString(R.string.back_side)})"

            viewModel.frontDocument.value == null ->
                "${getString(R.string.attach_file)}(${getString(R.string.rear_side)})"

            viewModel.contractDocument.value == null ->
                "${getString(R.string.attach_file)}(${getString(R.string.contract)})"

            socialStatusDropDownText.text.toString().isBlank() ->
                "${getString(R.string.fill_the_field)} ${getString(R.string.social_status)}"

            bankDropDownText.text.toString().isBlank() ->
                "${getString(R.string.fill_the_field)} ${getString(R.string.bank)}"

            ibanEditText.text.toString().isBlank() ->
                "${getString(R.string.fill_the_field)} ${getString(R.string.card_iban)}"

            userOccupationRadioGroup.checkedRadioButtonId == legalRadioBtn.id &&
                    legalEditText.text.toString().isBlank() ->
                "${getString(R.string.fill_the_field)} ${getString(R.string.individual_title)}"

            else -> null
        }
        if (message != null) {
            showMessage(
                context = requireContext(),
                message = message
            )
            return false
        }
        return true
    }
}