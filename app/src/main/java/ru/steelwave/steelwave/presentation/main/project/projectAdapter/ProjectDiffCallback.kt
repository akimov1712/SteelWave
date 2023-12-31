package ru.steelwave.steelwave.presentation.main.project.projectAdapter

import androidx.recyclerview.widget.DiffUtil
import ru.steelwave.steelwave.domain.entity.project.ProjectModel

class ProjectDiffCallback: DiffUtil.ItemCallback<ProjectModel>() {

    override fun areItemsTheSame(oldItem: ProjectModel, newItem: ProjectModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProjectModel, newItem: ProjectModel): Boolean {
        return oldItem == newItem
    }
}