package com.example.sunlightdesign.ui.screens.order.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Product
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.order.OrderViewModel
import com.example.sunlightdesign.ui.screens.order.adapters.ProductsOrderRecyclerAdapter
import kotlinx.android.synthetic.main.market_products_list.*
import kotlinx.android.synthetic.main.toolbar_with_back.*

class MarketFragment : StrongFragment<OrderViewModel>(OrderViewModel::class) {

    private lateinit var productsAdapter: ProductsOrderRecyclerAdapter
    private var spanCount = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.market_products_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.text = getString(R.string.market)

        setListeners()
        setObservers()

        viewModel.getProductList()

    }

    private fun setListeners() {
        btn_all_right.setOnClickListener {
//            val selectedProducts = productsAdapter.getCheckedProducts()
//            findNavController().navigate(R.id.action_stepThreeFragment_to_stepFourFragment)
        }
    }

    private fun setObservers() {
        viewModel.products.observe(viewLifecycleOwner, Observer {
            initRecycler(it.products ?: listOf())
        })
    }

    private fun initRecycler(items: List<Product>) {
        products_recycler_view.apply {
            productsAdapter = ProductsOrderRecyclerAdapter(items)
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = productsAdapter
        }
    }

}