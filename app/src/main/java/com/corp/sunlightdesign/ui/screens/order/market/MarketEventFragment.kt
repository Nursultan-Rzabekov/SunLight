package com.corp.sunlightdesign.ui.screens.order.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.TotalEvent
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.Event
import com.corp.sunlightdesign.ui.base.StrongFragment
import com.corp.sunlightdesign.ui.screens.order.OrderViewModel
import com.corp.sunlightdesign.ui.screens.order.adapters.EventsMarketRecyclerAdapter
import com.corp.sunlightdesign.ui.screens.order.sheetDialog.ChoosePaymentTypeBottomSheetDialog
import com.corp.sunlightdesign.ui.screens.order.sheetDialog.EventsBottomSheetDialog
import com.corp.sunlightdesign.ui.screens.order.sheetDialog.EventsConfirmBottomSheetDialog
import com.corp.sunlightdesign.ui.screens.order.sheetDialog.ResponseBottomSheetDialog
import com.corp.sunlightdesign.ui.screens.wallet.WalletViewModel
import com.corp.sunlightdesign.ui.screens.wallet.withdraw.dialogs.ChooseOfficeBottomSheetDialog
import kotlinx.android.synthetic.main.market_event_products_list.*
import kotlinx.android.synthetic.main.toolbar_with_back.*

class MarketEventFragment : StrongFragment<OrderViewModel>(OrderViewModel::class),
    EventsMarketRecyclerAdapter.EventMarketItemSelected,
    EventsBottomSheetDialog.EventInteraction,
    EventsConfirmBottomSheetDialog.EventConfirmInteraction,
    ChoosePaymentTypeBottomSheetDialog.ChooseTypeInteraction,
    ChooseOfficeBottomSheetDialog.ChooseOfficeDialogInteraction {

    private lateinit var eventsAdapter: EventsMarketRecyclerAdapter
    private var spanCount = 2

    private lateinit var eventsBottomSheetDialog: EventsBottomSheetDialog
    private lateinit var eventsConfirmBottomSheetDialog: EventsConfirmBottomSheetDialog
    private lateinit var chooseOfficeBottomSheetDialog: ChooseOfficeBottomSheetDialog
    private lateinit var choosePaymentTypeBottomSheetDialog: ChoosePaymentTypeBottomSheetDialog
    private lateinit var responseBottomSheetDialog: ResponseBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.market_event_products_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.text = getString(R.string.market)

        setObservers()

        viewModel.getEventList()

    }

    override fun setEventState(event: Event) {
        showEventBottomSheetDialog(event)
    }


    private fun setObservers() = with(viewModel) {
        progress.observe(viewLifecycleOwner, Observer {
            marketProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        events.observe(viewLifecycleOwner, Observer {
            if (it.events.isNullOrEmpty()) {
                no_event_iv.visibility = View.VISIBLE
                no_event_tv.visibility = View.VISIBLE
            } else {
                no_event_iv.visibility = View.GONE
                no_event_tv.visibility = View.GONE
            }
            initRecycler(it.events ?: listOf())
        })

        officesList.observe(viewLifecycleOwner, Observer {
            val citiesList = ArrayList<WalletViewModel.ShortenedCity>()
            it.offices?.forEach { office ->
                office?.city?.id ?: return@forEach
                citiesList.add(
                    WalletViewModel.ShortenedCity(
                        office.city.id,
                        office.city.city_name.toString()
                    )
                )
            }
            chooseOfficeBottomSheetDialog = ChooseOfficeBottomSheetDialog(
                this@MarketEventFragment,
                it.offices as ArrayList<com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Office?>,
                citiesList,
                true
            )
            chooseOfficeBottomSheetDialog.show(
                parentFragmentManager,
                ChooseOfficeBottomSheetDialog.TAG
            )
        })

        eventState.observe(viewLifecycleOwner, Observer {
            responseBottomSheetDialog =
                ResponseBottomSheetDialog(isSuccess = it.isSuccess, message = it.message)
            responseBottomSheetDialog.show(
                parentFragmentManager,
                ResponseBottomSheetDialog.TAG
            )
        })
    }

    private fun initRecycler(items: List<Event>) {
        products_recycler_view.apply {
            eventsAdapter = EventsMarketRecyclerAdapter(items, this@MarketEventFragment)
            val manager = GridLayoutManager(requireContext(), spanCount)
            layoutManager = manager
            adapter = eventsAdapter
        }
    }

    private fun showEventBottomSheetDialog(event: Event) {
        eventsBottomSheetDialog = EventsBottomSheetDialog(
            this@MarketEventFragment,
            event = event
        )
        eventsBottomSheetDialog.show(
            parentFragmentManager,
            EventsBottomSheetDialog.TAG
        )
    }

    override fun onEventSelected(event: Event) {
        val bundle = bundleOf(
            "item" to event
        )
        findNavController().navigate(
            R.id.market_event_fragment_to_market_event_details_fragment,
            bundle
        )
    }


    override fun onEventListSelected(totalEvent: TotalEvent) {
        eventsBottomSheetDialog.dismiss()

        viewModel.createEvent.child = totalEvent.child
        viewModel.createEvent.adult = totalEvent.adult
        viewModel.createEvent.eventId = totalEvent.eventId
        viewModel.createEvent.comment = totalEvent.comment

        showEventConfirmBottomSheetDialog(totalEvent)

    }

    private fun showEventConfirmBottomSheetDialog(totalEvent: TotalEvent) {
        eventsConfirmBottomSheetDialog = EventsConfirmBottomSheetDialog(
            this@MarketEventFragment,
            totalEvent = totalEvent
        )
        eventsConfirmBottomSheetDialog.show(
            parentFragmentManager,
            EventsConfirmBottomSheetDialog.TAG
        )
    }

    override fun onEventConfirmed() {
        eventsConfirmBottomSheetDialog.dismiss()
        showPaymentTypeDialog()
    }

    private fun showPaymentTypeDialog(isHideTill: Boolean = false) {
        choosePaymentTypeBottomSheetDialog =
            ChoosePaymentTypeBottomSheetDialog(this@MarketEventFragment, isHideTill)
        choosePaymentTypeBottomSheetDialog.show(
            parentFragmentManager,
            ChoosePaymentTypeBottomSheetDialog.TAG
        )
    }

    override fun onTypeSelected(type: Int) {
        choosePaymentTypeBottomSheetDialog.dismiss()

        if (type == 2) {
            viewModel.createEvent.payment = 1
            viewModel.buyEvent(viewModel.createEvent.build())
        } else {
            viewModel.getOfficesList()
        }

    }

    override fun onNextBtnPressed(officeId: Int) {
        chooseOfficeBottomSheetDialog.dismiss()
        viewModel.createEvent.payment = 2
        viewModel.createEvent.officeId = officeId
        viewModel.buyEvent(viewModel.createEvent.build())
    }
}