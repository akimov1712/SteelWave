package ru.steelwave.steelwave.presentation.modals.choiceProjectAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.steelwave.steelwave.databinding.ItemChoiceProjectBinding
import ru.steelwave.steelwave.domain.entity.project.ProjectModel


class ChoiceProjectAdapter: ListAdapter<ProjectModel, ChoiceProjectViewHolder>(
    ChoiceProjectDiffCallback()
) {

    var onChoiceProjectClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceProjectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChoiceProjectBinding.inflate(inflater, parent, false)
        return ChoiceProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChoiceProjectViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            ivProject.setImageBitmap(item.previewImage)
            ivProject.setOnClickListener {
                onChoiceProjectClickListener?.invoke(item.id)
            }
        }
    }
}