package com.example.sunlightdesign.ui.screens.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Order
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.order.adapters.OrdersRecyclerAdapter
import kotlinx.android.synthetic.main.orders_fragment.*
import kotlinx.android.synthetic.main.toolbar_with_back.*


class OrdersFragment : StrongFragment<OrderViewModel>(OrderViewModel::class), OrdersRecyclerAdapter.OrderSelector {

    private val ordersRecyclerAdapter: OrdersRecyclerAdapter by lazy {
        return@lazy OrdersRecyclerAdapter(requireContext(),this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.orders_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        titleTextView.text = getString(R.string.my_orders)

        initRecyclerView()
        configViewModel()
        viewModel.getMyOrders()

    }


    private fun configViewModel() {
        viewModel.apply {
            orders.observe(viewLifecycleOwner, Observer {
                ordersRecyclerAdapter.setItems(it.orders as MutableList<Order>)
            })
        }
    }

    private fun initRecyclerView(){
        orderRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ordersRecyclerAdapter
        }
    }

    override fun onOrderSelected(id: Int) {

    }

}
