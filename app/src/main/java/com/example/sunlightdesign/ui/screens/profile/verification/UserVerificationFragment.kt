package com.example.sunlightdesign.ui.screens.profile.verification

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.utils.getFileName
import com.example.sunlightdesign.utils.getFileSizeInLong
import kotlinx.android.synthetic.main.document_layout_item.view.*
import kotlinx.android.synthetic.main.fragment_verify_user.*
import kotlinx.android.synthetic.main.toolbar_with_back.*

class UserVerificationFragment: StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

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
        entepreneurChecked()
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
    }

    private fun entepreneurChecked() {
        companyTitleLabelTextView.visibility = View.GONE
        companyTitleLayout.visibility = View.GONE
    }

    private fun legalChecked() {
        companyTitleLabelTextView.visibility = View.VISIBLE
        companyTitleLayout.visibility = View.VISIBLE
    }

    private fun addDocumentView(uri: Uri) {
        val view = layoutInflater.inflate(R.layout.document_layout_item, null)
        view.documentNameTextView.text = getFileName(requireContext(), uri)
        view.documentSizeTextView.text = getFileSizeInLong(requireContext(), uri).toString()
        view.documentRemoveTextView.setOnClickListener {
            documentsContainer.removeView(view)
        }

        documentsContainer.addView(view)
    }
}