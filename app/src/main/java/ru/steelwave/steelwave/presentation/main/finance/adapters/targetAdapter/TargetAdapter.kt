package ru.steelwave.steelwave.presentation.main.finance.adapters.targetAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.steelwave.steelwave.R
import ru.steelwave.steelwave.databinding.ItemTargetBinding
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.utils.formatPrice

class TargetAdapter: ListAdapter<TargetModel, TargetViewHolder>(TargetDiffCallback()) {

    var onClickBtnMoreListener: ((Int, View) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TargetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTargetBinding.inflate(inflater, parent, false)
        return TargetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TargetViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvNameTarget.text = item.name
            tvCollected.text = "$" + formatPrice(item.collectedPrice)
            tvTotal.text = "$" + formatPrice(item.totalPrice)
            val percent = ((item.collectedPrice.toFloat() / item.totalPrice.toFloat()) * 100).toInt()
            tvPercent.text = "$percent%"
            circularProgressBar.progress = percent.toFloat()

            btnMore.btnMore.setOnClickListener{
                onClickBtnMoreListener?.invoke(item.id, it)
            }
        }
    }
}