package com.example.sunlightdesign.ui.screens.order

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Office
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Order
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.order.sheetDialog.MyOrdersBottomSheetDialog
import com.example.sunlightdesign.ui.screens.order.adapters.OrdersRecyclerAdapter
import com.example.sunlightdesign.ui.screens.order.market.MarketActivity
import com.example.sunlightdesign.ui.screens.order.sheetDialog.ChoosePaymentTypeBottomSheetDialog
import com.example.sunlightdesign.ui.screens.order.sheetDialog.RepeatsOrdersBottomSheetDialog
import com.example.sunlightdesign.ui.screens.order.sheetDialog.SuccessBottomSheetDialog
import com.example.sunlightdesign.ui.screens.wallet.withdraw.dialogs.ChooseOfficeBottomSheetDialog
import kotlinx.android.synthetic.main.orders_fragment.*
import kotlinx.android.synthetic.main.toolbar_with_back.*


class OrdersFragment : StrongFragment<OrderViewModel>(OrderViewModel::class),
    OrdersRecyclerAdapter.OrderSelector,
    MyOrdersBottomSheetDialog.ReplyOrderInteraction,
    RepeatsOrdersBottomSheetDialog.RepeatsOrderInteraction,
    ChooseOfficeBottomSheetDialog.ChooseOfficeDialogInteraction,
    ChoosePaymentTypeBottomSheetDialog.ChooseTypeInteraction {

    private val ordersRecyclerAdapter: OrdersRecyclerAdapter by lazy {
        return@lazy OrdersRecyclerAdapter(requireContext(),this)
    }

    private lateinit var myOrdersBottomSheetDialog: MyOrdersBottomSheetDialog
    private lateinit var repeatsOrdersBottomSheetDialog: RepeatsOrdersBottomSheetDialog
    private lateinit var chooseOfficeBottomSheetDialog: ChooseOfficeBottomSheetDialog
    private lateinit var choosePaymentTypeBottomSheetDialog: ChoosePaymentTypeBottomSheetDialog
    private lateinit var successBottomSheetDialog: SuccessBottomSheetDialog

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

        goToMarketLayout.setOnClickListener {
            startActivity(Intent(requireContext(), MarketActivity::class.java))
        }

    }

    private fun configViewModel() {
        viewModel.apply {
            orders.observe(viewLifecycleOwner, Observer {
                ordersRecyclerAdapter.setItems(it.orders as MutableList<Order>)
            })

            orderState.observe(viewLifecycleOwner, Observer {
                if (it.isSuccess) {
                    successBottomSheetDialog = SuccessBottomSheetDialog()
                    successBottomSheetDialog.show(
                        parentFragmentManager,
                        SuccessBottomSheetDialog.TAG
                    )
                }
            })
            officesList.observe(viewLifecycleOwner, Observer {
                chooseOfficeBottomSheetDialog = ChooseOfficeBottomSheetDialog(this@OrdersFragment, it.offices as ArrayList<com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Office?>)
                chooseOfficeBottomSheetDialog.show(
                    parentFragmentManager,
                    ChooseOfficeBottomSheetDialog.TAG
                )
            })
        }
    }

    private fun initRecyclerView(){
        orderRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ordersRecyclerAdapter
        }
    }

    override fun onOrderSelected(order: Order) {
        myOrdersBottomSheetDialog = MyOrdersBottomSheetDialog(replyOrderInteraction = this, order = order)
        myOrdersBottomSheetDialog.show(
            parentFragmentManager,
            MyOrdersBottomSheetDialog.TAG
        )
    }

    override fun onReplyOrderSelected(order: Order) {
        myOrdersBottomSheetDialog.dismiss()

        order.user.id?.let {
            viewModel.createOrderBuilder.user_id = it
        }

        repeatsOrdersBottomSheetDialog = RepeatsOrdersBottomSheetDialog(order = order,repeatsOrderInteraction = this)
        repeatsOrdersBottomSheetDialog.show(
            parentFragmentManager,
            RepeatsOrdersBottomSheetDialog.TAG
        )
    }

    override fun onRepeatsOrderSelected(order: Order) {
        repeatsOrdersBottomSheetDialog.dismiss()

        with(order.products){
            if(!this.isNullOrEmpty()){
                val products = mutableListOf<Product>()
                order.products?.forEach {
                    it.product?.let { it1 -> products.add(it1) }
                }
                viewModel.createOrderBuilder.products = products
            }
        }
        viewModel.createOrderBuilder.payment_sum = order.total_price ?: -0.0

        viewModel.getOfficesList()
    }

    override fun onNextBtnPressed(officeId: Int) {
        chooseOfficeBottomSheetDialog.dismiss()

        viewModel.createOrderBuilder.office_id = officeId

        choosePaymentTypeBottomSheetDialog = ChoosePaymentTypeBottomSheetDialog(this@OrdersFragment)
        choosePaymentTypeBottomSheetDialog.show(
            parentFragmentManager,
            ChoosePaymentTypeBottomSheetDialog.TAG
        )
    }

    override fun onTypeSelected(type: Int) {
        choosePaymentTypeBottomSheetDialog.dismiss()

        viewModel.createOrderBuilder.order_payment_type = type

        viewModel.storeOrder(createOrderPartner = viewModel.createOrderBuilder.build())
    }
}
