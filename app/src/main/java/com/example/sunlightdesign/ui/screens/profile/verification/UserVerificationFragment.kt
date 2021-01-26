package com.example.sunlightdesign.ui.screens.profile.verification

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.BankName
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.SocialStatusArr
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.VerifyImage
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.register.adapters.CustomPopupAdapter
import com.example.sunlightdesign.usecase.usercase.profileUse.post.VerificationRequest
import com.example.sunlightdesign.utils.IIN_MASK
import com.example.sunlightdesign.utils.MaskUtils
import com.example.sunlightdesign.utils.isIinValid
import com.example.sunlightdesign.utils.showMessage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_verify_user.*
import kotlinx.android.synthetic.main.toolbar_with_back.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class UserVerificationFragment:
    StrongFragment<UserVerificationViewModel>(UserVerificationViewModel::class) {

    private lateinit var banksAdapter: CustomPopupAdapter<String>
    private lateinit var socialAdapter: CustomPopupAdapter<String>
    private lateinit var socialDialog: AlertDialog

    private val documentsAdapter = DocumentAttachmentAdapter(mutableListOf())

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
        setupRecyclerView()
        entepreneurChecked()
        setupObservers()

        viewModel.getHelper()
    }

    private fun setupObservers() {
        viewModel.apply {

            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })

            attachDocument.observe(viewLifecycleOwner, Observer {
                documentsAdapter.addItem(DocumentAttachmentEntity(
                    url = null,
                    uri = it
                ))
            })

            helper.observe(viewLifecycleOwner, Observer {
                it.bank_names?.let { banks ->
                    setupBankList(banks.map { bank -> bank.name.toString() })
                }
                viewModel.getInitialVerificationInfo()
            })

            verificationInfo.observe(viewLifecycleOwner, Observer {
                firstNameEditText.setText(it.verify?.name)
                lastNameEditText.setText(it.verify?.surname)
                middleNameEditText.setText(it.verify?.middle_name)
                iinEditText.setText(it.verify?.iin)
                ibanEditText.setText(it.verify?.iban)
                bankDropDownText.setText(it.verify?.bank?.name)

                if (it.verify?.type.toString() == VerificationRequest.TYPE_LEGAL) {
                    userOccupationRadioGroup.check(legalRadioBtn.id)
                    legalChecked()
                    legalEditText.setText(it.verify?.ip)
                } else {
                    userOccupationRadioGroup.check(physRadioBtn.id)
                    entepreneurChecked()
                }

                val socials = it.verify?.social_status?.map { social -> social.name.toString() }
                val socialsText = socials?.joinToString("\n")
                socialStatusDropDownText.setText(socialsText)
                banksAdapter.callFiltering("")

                setInitialDocuments(it.verify_images)
            })

            verificationState.observe(viewLifecycleOwner, Observer {
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

    private fun setupRecyclerView() {
        documentsRecyclerView.adapter = documentsAdapter
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
            val option = adapter.getItem(position) as String
            if (position == items.size - 1 &&
                option.trim().toLowerCase(Locale.getDefault()) == getString(R.string.another)) {
                anotherBankLayout.visibility = View.VISIBLE
            } else {
                anotherBankLayout.visibility = View.GONE
            }
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
            .setNegativeButton(getString(R.string.text_cancel)) { _, _ -> Unit}

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

    private fun setInitialDocuments(documents: List<VerifyImage>?) {
        documents ?: return
        documents.forEach {
            documentsAdapter.addItem(DocumentAttachmentEntity(
                uri = null,
                url = it.verify_path
            ))
        }
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
        val type = when (userOccupationRadioGroup.checkedRadioButtonId) {
            legalRadioBtn.id -> VerificationRequest.TYPE_LEGAL
            else -> VerificationRequest.TYPE_NOT_LEGAL
        }
        val ip = when (userOccupationRadioGroup.checkedRadioButtonId) {
            legalRadioBtn.id -> legalEditText.text.toString().trim()
            else -> null
        }

        val gson = Gson()
        val social = gson.toJson(viewModel.selectedSocialStatuses.map { SocialStatusArr(it) })
        var bank = gson.toJson(BankName(bankDropDownText.text.toString().trim()))
        if (anotherBankLayout.visibility == View.VISIBLE) {
            bank = gson.toJson(BankName(anotherBankEditText.text.toString().trim()))
        }

        val files = createMultipartFiles(documentsAdapter.getLocalFiles())

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
                images = files
            )
        )

    }

    private fun createMultipartFiles(files: List<Uri>): List<MultipartBody.Part> {
        val documents = mutableListOf<MultipartBody.Part>()
        files.forEachIndexed { index, it ->
            val inputStream =
                requireActivity().contentResolver?.openInputStream(it) ?: return@forEachIndexed
            val part = MultipartBody.Part.createFormData(
                "images[]", "file$index.jpeg", RequestBody.create(
                    MediaType.parse("image/*"),
                    inputStream.readBytes()
                )
            )
            documents.add(part)
        }
        return documents
    }
}