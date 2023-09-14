package ru.steelwave.steelwave.presentation.main.finance.adapters.targetAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.steelwave.steelwave.databinding.ItemTargetBinding
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.utils.formatNumber

class TargetAdapter: ListAdapter<TargetModel, TargetViewHolder>(TargetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTargetBinding.inflate(inflater, parent, false)
        return TargetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TargetViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            if (position == 0){
                val layoutParams = cvTarget.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(120,0,0,0)
            }
            if (position == itemCount-1){
                val layoutParams = cvTarget.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(0,0,120,0)
            }
            tvNameTarget.text = item.name
            tvCollected.text = "$" + formatNumber(item.collectedPrice)
            tvTotal.text = "$" + formatNumber(item.totalPrice)
            val percent = ((item.collectedPrice.toFloat() / item.totalPrice.toFloat()) * 100).toInt()
            tvPercent.text = percent.toString() + "%"
            circularProgressBar.progress = percent.toFloat()
        }
    }
}