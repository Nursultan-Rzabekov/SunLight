package com.example.sunlightdesign.ui.screens.profile.verification

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MultiAutoCompleteTextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.list.MultiChoiceListener
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Users
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.register.adapters.CustomPopupAdapter
import com.example.sunlightdesign.usecase.usercase.profileUse.post.VerificationRequest
import com.example.sunlightdesign.utils.*
import kotlinx.android.synthetic.main.document_layout_item.view.*
import kotlinx.android.synthetic.main.fragment_register_partner_step_one.*
import kotlinx.android.synthetic.main.fragment_verify_user.*
import kotlinx.android.synthetic.main.toolbar_with_back.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import timber.log.Timber
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList

class UserVerificationFragment:
    StrongFragment<UserVerificationViewModel>(UserVerificationViewModel::class) {

    private lateinit var banksAdapter: CustomPopupAdapter<String>
    private lateinit var socialAdapter: CustomPopupAdapter<String>
    private lateinit var socialDialog: AlertDialog

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
        setupObservers()

        viewModel.getHelper()
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

            helper.observe(viewLifecycleOwner, Observer {
                it.bank_names?.let { banks ->
                    setupBankList(banks.map { bank -> bank.name.toString() })
                }
            })

            verify.observe(viewLifecycleOwner, Observer {
                if (it.verify?.id != null) {
                    showMessage(
                        requireContext(),
                        message = getString(R.string.text_success),
                        btnPositiveEvent = DialogInterface.OnClickListener { _, _ ->
                            viewModel.withActivity { activity -> activity.finish() }
                        }
                    )
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

        sendVerificationBtn.setOnClickListener {
            if (checkFields()) verify()
        }

        socialStatusDropDown.setEndIconOnClickListener {
            showSocialListDialog(viewModel.helper.value?.social_status_arr
                ?.map{ it.name.toString() } ?: listOf())
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
        bankDropDownText.setOnItemClickListener { _, _, position, _ ->
            val adapter = bankDropDownText.adapter
            Timber.d("sponsor: ${adapter.getItem(position)}")
        }
    }

    private fun showSocialListDialog(items: List<String>) {

        val selectedItems = arrayListOf<String>()

        val alertBuilder = AlertDialog.Builder(requireContext())
            .setTitle(R.string.social_status)
            .setMultiChoiceItems(
                items.toTypedArray(),
                null
            ) { _, which, isChecked -> if (isChecked) selectedItems.add(items[which]) }
            .setPositiveButton(getString(R.string.text_ok)) { _, _ ->
                viewModel.selectedSocialStatuses.clear()
                viewModel.selectedSocialStatuses.addAll(selectedItems)

                val formattedText = selectedItems.joinToString("\n")
                socialStatusDropDownText.setText(formattedText)
            }
        socialDialog = alertBuilder.create()
        socialDialog.show()
    }

    private fun setupSocialList(items: List<String>) {
        socialAdapter = CustomPopupAdapter(
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
        socialStatusDropDownText.threshold = 1
        socialStatusDropDownText.setAdapter(socialAdapter)
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

    private fun verify() {
        val name = firstNameEditText.text.toString().trim()
        val middleName = middleNameEditText.text.toString().trim()
        val lastName = lastNameEditText.text.toString().trim()
        val iin = iinEditText.text.toString().trim()
        val iban = ibanEditText.text.toString().trim()
        val bank = bankDropDownText.text.toString().trim()
        val type = when (userOccupationRadioGroup.checkedRadioButtonId) {
            legalRadioBtn.id -> VerificationRequest.TYPE_LEGAL
            else -> VerificationRequest.TYPE_NOT_LEGAL
        }
        val ip = when (userOccupationRadioGroup.checkedRadioButtonId) {
            legalRadioBtn.id -> legalEditText.text.toString().trim()
            else -> null
        }
        val social = socialStatusDropDownText.text.toString().trim()

        val backPath = viewModel.backDocument.value
        val frontPath = viewModel.frontDocument.value
        val contractPath = viewModel.contractDocument.value

        backPath ?: return
        frontPath ?: return
        contractPath ?: return

        val backInputStream =
            requireActivity().contentResolver?.openInputStream(backPath) ?: return

        val frontInputStream =
            requireActivity().contentResolver?.openInputStream(frontPath) ?: return

        val contractInputStream =
            requireActivity().contentResolver?.openInputStream(contractPath) ?: return

        val backPart = MultipartBody.Part.createFormData(
            "images[]", "back.jpeg", RequestBody.create(
                MediaType.parse("image/*"),
                backInputStream.readBytes()
            )
        )

        val frontPart = MultipartBody.Part.createFormData(
            "images[]", "front.jpg", RequestBody.create(
                MediaType.parse("image/*"),
                frontInputStream.readBytes()
            )
        )

        val contractPart = MultipartBody.Part.createFormData(
            "images[]", "contract.jpg", RequestBody.create(
                MediaType.parse("image/*"),
                contractInputStream.readBytes()
            )
        )

        viewModel.verifyUser(
            VerificationRequest(
                name = name,
                surname = lastName,
                middle_name = middleName,
                iin = iin,
                iban = iban,
                ip = ip,
                type = type,
                bank = bank,
                social_status = social,
                images = listOf(frontPart, backPart, contractPart)
            )
        )

    }
}