

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
import kotlinx.android.synthetic.main.fragment_register_partner_step_three.*


class RegisterFragmentStepThree : StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

    private lateinit var productsAdapter: ProductsRecyclerAdapter
    private var spanCount = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_partner_step_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()
        setObservers()
    }

    private fun setListeners(){
        next_step_three_btn.setOnClickListener {
            findNavController().navigate(R.id.action_stepThreeFragment_to_stepFourFragment)
        }
    }

    private fun setObservers() {
        viewModel.productsList.observe(viewLifecycleOwner, Observer {
            initRecycler(it ?: listOf())
        })
    }

    private fun initRecycler(items: List<Product>){
        products_recycler_view.apply {
            productsAdapter = ProductsRecyclerAdapter(items)
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = productsAdapter
        }
    }

}
