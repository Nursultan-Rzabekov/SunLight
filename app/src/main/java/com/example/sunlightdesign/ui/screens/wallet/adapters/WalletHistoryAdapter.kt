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
import com.example.sunlightdesign.utils.EmptyViewHolder
import kotlinx.android.synthetic.main.wallet_history_item.view.*

class WalletHistoryAdapter(
    private val interaction: WalletHistoryInteraction
): ListAdapter<Data, RecyclerView.ViewHolder>(diffUtil) {

    companion object{
        private val diffUtil = object: DiffUtil.ItemCallback<Data>(){
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(
                oldItem: Data,
                newItem: Data
            ): Boolean = oldItem.id == newItem.id

        }

        private const val EMPTY_LIST = -1
        private const val NOT_EMPTY_LIST = 0

        val EMPTY = Data(
            bonus = null,
            created_at = "",
            extra_data = "",
            finish_date = "",
            history_type = "",
            id = null,
            main_wallet_new = 0.0,
            main_wallet_old = 0.0,
            order = null,
            purchase_wallet_new = 0.0,
            purchase_wallet_old = 0.0,
            registry_wallet_new = 0.0,
            registry_wallet_old = 0.0,
            start_date = "",
            status_type = "",
            user_id = 0.0,
            value = 0.0,
            wallet_id = 0.0,
            wallet_type = "",
            withdraw = null
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EMPTY_LIST -> {
                EmptyViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(EmptyViewHolder.getLayoutId(), parent, false))
            }
            else -> {
                WalletHistoryViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.wallet_history_item, parent, false),
                    interaction = interaction
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WalletHistoryViewHolder -> {
                holder.bind(getItem(position))
            }
            is EmptyViewHolder -> {
                holder.bind(holder.itemView.context.getString(R.string.empty_withdraw_history))
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (currentList.size == 1 && currentList.first() == EMPTY) {
            EMPTY_LIST
        } else {
            NOT_EMPTY_LIST
        }

    class WalletHistoryViewHolder(
        view: View,
        private val interaction: WalletHistoryInteraction
    ): RecyclerView.ViewHolder(view){
        fun bind(history: Data) {
            itemView.typeOfTransactionTextView.text = history.status_type
            itemView.accountTextView.text = history.wallet_type
            itemView.amountTextView.text = itemView.context.getString(R.string.amount_formatted, history.value)
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