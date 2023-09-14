package ru.steelwave.steelwave.presentation.main.finance.adapters.incomeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.steelwave.steelwave.databinding.ItemStatsBinding
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import ru.steelwave.steelwave.utils.formatNumber

class IncomeAdapter: ListAdapter<TransactionModel, IncomeViewHolder>(IncomeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStatsBinding.inflate(inflater, parent, false)
        return IncomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvNameIncome.text = item.name
            tvIncome.text = "$" + formatNumber(item.count)
        }
    }
}