package com.example.sunlightdesign.ui.screens.wallet.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.Data
import com.example.sunlightdesign.utils.Constants
import com.example.sunlightdesign.utils.DateUtils
import kotlinx.android.synthetic.main.wallet_history_item.view.*

class WalletHistoryAdapter(
    private val interaction: WalletHistoryInteraction
): ListAdapter<Data, WalletHistoryAdapter.WalletHistoryViewHolder>(diffUtil) {

    companion object{
        private val diffUtil = object: DiffUtil.ItemCallback<Data>(){
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: Data,
                newItem: Data
            ): Boolean = oldItem.id == newItem.id

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletHistoryViewHolder {
       return WalletHistoryViewHolder(
           LayoutInflater.from(parent.context)
               .inflate(R.layout.wallet_history_item, parent, false),
           interaction = interaction
       )
    }

    override fun onBindViewHolder(holder: WalletHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WalletHistoryViewHolder(
        view: View,
        private val interaction: WalletHistoryInteraction
    ): RecyclerView.ViewHolder(view){
        fun bind(history: Data) {
            itemView.typeOfTransactionTextView.text = history.status_type
            itemView.accountTextView.text = history.wallet_type
            itemView.amountTextView.text = itemView.context.getString(R.string.amount_bv, history.value)
            itemView.dateOfTransactionTextView.text =
                DateUtils.reformatDateString(history.finish_date, DateUtils.PATTERN_DD_MM_YYYY)

            itemView.bonusesTextView.text = history.bonus?.bonus_name
            history.bonus?.created_at?.let {
                itemView.createDateTextView.text =
                    DateUtils.reformatDateString(it, DateUtils.PATTERN_DD_MM_YYYY)
            }
            itemView.walletTextView.text = history.wallet_type
            itemView.nameTextView.text = history.bonus?.bonus_description

            itemView.typeOfTransactionImageView.setImageDrawable(
                ContextCompat.getDrawable(itemView.context,
                    if (history.status_type == Constants.TRANSACTION_TYPE_INCOME) {
                        R.drawable.ic_income
                    } else {
                        R.drawable.ic_consumption
                    }
                )
            )

            itemView.walletHistoryLayout.setOnClickListener {
                itemView.expandImageView.setImageDrawable(
                    if (itemView.additionalInfoLayout.isVisible) {
                        ContextCompat.getDrawable(itemView.context, R.drawable.ic_baseline_keyboard_arrow_down)
                    } else {
                        ContextCompat.getDrawable(itemView.context, R.drawable.ic_baseline_keyboard_arrow_up)
                    }
                )
                itemView.additionalInfoLayout.isVisible = !itemView.additionalInfoLayout.isVisible
                interaction.onExpand()
            }
        }
    }

    interface WalletHistoryInteraction{
        fun onExpand()
    }
}