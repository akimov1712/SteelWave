package ru.steelwave.steelwave.presentation.main.finance.adapters.incomeAdapter

import androidx.recyclerview.widget.DiffUtil
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel

class IncomeDiffCallback: DiffUtil.ItemCallback<TransactionModel>() {

    override fun areItemsTheSame(oldItem: TransactionModel, newItem: TransactionModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TransactionModel, newItem: TransactionModel): Boolean {
        return oldItem == newItem
    }
}