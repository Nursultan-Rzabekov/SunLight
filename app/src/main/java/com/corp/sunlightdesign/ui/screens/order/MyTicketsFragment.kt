package com.corp.sunlightdesign.ui.screens.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.Ticket
import com.corp.sunlightdesign.ui.base.StrongFragment
import com.corp.sunlightdesign.ui.screens.order.adapters.TicketsRecyclerAdapter
import kotlinx.android.synthetic.main.orders_fragment.ordersProgressBar
import kotlinx.android.synthetic.main.tickets_fragment.*
import kotlinx.android.synthetic.main.toolbar_with_back.*

class MyTicketsFragment : StrongFragment<OrderViewModel>(OrderViewModel::class),
    TicketsRecyclerAdapter.TicketsSelector {

    private val ticketsRecyclerAdapter: TicketsRecyclerAdapter by lazy {
        return@lazy TicketsRecyclerAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tickets_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titleTextView.text = getString(R.string.go_to_my_ticket)

        initRecyclerView()
        configViewModel()
        viewModel.getMyTickets()

        goToMarketEventLayout.setOnClickListener {
            findNavController().navigate(R.id.my_tickets_fragments_to_market_event_fragment)
        }
    }

    private fun initRecyclerView() {
        ticketRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ticketsRecyclerAdapter
        }
    }

    private fun configViewModel() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                ordersProgressBar.visibility = if (it) View.VISIBLE else View.GONE
            })
            tickets.observe(viewLifecycleOwner, Observer {
                if (it.events.isNullOrEmpty()) {
                    no_event_iv.visibility = View.VISIBLE
                    no_event_tv.visibility = View.VISIBLE
                    no_event_desc_tv.visibility = View.VISIBLE
                } else {
                    no_event_iv.visibility = View.GONE
                    no_event_tv.visibility = View.GONE
                    no_event_desc_tv.visibility = View.GONE
                }
                ticketsRecyclerAdapter.setItems(it.events as MutableList<Ticket>)
            })
        }
    }

    override fun onOrderSelected(ticket: Ticket) {
        findNavController().navigate(
            R.id.my_tickets_fragments_to_market_event_details_fragment,
            bundleOf("item" to ticket)
        )
    }
}