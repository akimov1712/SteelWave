package ru.steelwave.steelwave.presentation.main.finance.adapters.targetAdapter

import androidx.recyclerview.widget.DiffUtil
import ru.steelwave.steelwave.domain.entity.finance.TargetModel

class TargetDiffCallback: DiffUtil.ItemCallback<TargetModel>() {

    override fun areItemsTheSame(oldItem: TargetModel, newItem: TargetModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TargetModel, newItem: TargetModel): Boolean {
        return oldItem == newItem
    }
}