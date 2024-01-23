package ru.steelwave.steelwave.presentation.main.finance.adapters.yearIncomeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.steelwave.steelwave.databinding.ItemIncomeYearBinding
import ru.steelwave.steelwave.domain.entity.finance.TransactionModel
import ru.steelwave.steelwave.utils.formatPrice

class YearIncomeAdapter: ListAdapter<TransactionModel, YearIncomeViewHolder>(YearIncomeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearIncomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIncomeYearBinding.inflate(inflater, parent, false)
        return YearIncomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YearIncomeViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvNameIncome.text = item.name
            tvIncome.text = "$" + formatPrice(item.count)
        }
    }
}