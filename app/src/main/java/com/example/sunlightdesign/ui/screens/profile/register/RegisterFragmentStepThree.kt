package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.ui.screens.profile.register.adapters.ProductsRecyclerAdapter
import com.example.sunlightdesign.utils.showMessage
import kotlinx.android.synthetic.main.fragment_register_partner_step_three.*


class RegisterFragmentStepThree : StrongFragment<ProfileViewModel>(ProfileViewModel::class),
    ProductsRecyclerAdapter.ProductsItemSelected {

    companion object{
        const val PACKAGE_NAME = "package_name"
    }

    private lateinit var productsAdapter: ProductsRecyclerAdapter
    private var spanCount = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? = inflater
        .inflate(R.layout.fragment_register_partner_step_three, container, false)


    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        arguments?.let {
            available_products_from_tv.text = getString(R.string.available_products_from,
                it.getString(PACKAGE_NAME))
        }
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        next_step_three_btn.setOnClickListener {
            if (!checkFields()) return@setOnClickListener
            viewModel.createOrderPartnerBuilder.products = productsAdapter.getCheckedProducts()
            findNavController().navigate(R.id.action_stepThreeFragment_to_stepFourFragment)
        }

        back_step_three_btn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setObservers() {
        viewModel.productsList.observe(viewLifecycleOwner, Observer {
            initRecycler(it ?: listOf())
        })
    }

    private fun initRecycler(items: List<Product>) {
        products_recycler_view.apply {
            productsAdapter = ProductsRecyclerAdapter(items = items,productsItemSelected = this@RegisterFragmentStepThree)
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = productsAdapter
        }
    }

    override fun onProductsSelected(product: Product) {

    }

    private fun checkFields() : Boolean {
        val message = when {
            productsAdapter.getCheckedProducts().isEmpty() -> "Выберите продукт"
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
