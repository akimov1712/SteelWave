package ru.steelwave.steelwave.presentation.main.report.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.steelwave.steelwave.domain.entity.user.TaskModel

class TaskDiffCallback: DiffUtil.ItemCallback<TaskModel>() {

    override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
        return oldItem == newItem
    }
}