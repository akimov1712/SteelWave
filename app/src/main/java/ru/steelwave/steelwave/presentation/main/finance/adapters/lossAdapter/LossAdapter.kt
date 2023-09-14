package ru.steelwave.steelwave.presentation.main.finance.adapters.lossAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.steelwave.steelwave.databinding.ItemLossBinding
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import ru.steelwave.steelwave.utils.formatNumber

class LossAdapter: ListAdapter<TransactionModel, LossViewHolder>(LossDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LossViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLossBinding.inflate(inflater, parent, false)
        return LossViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LossViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvNameIncome.text = item.name
            tvIncome.text = "$" + formatNumber(item.count)
        }
    }
}