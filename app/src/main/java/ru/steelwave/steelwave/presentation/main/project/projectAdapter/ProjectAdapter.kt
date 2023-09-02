package ru.steelwave.steelwave.presentation.main.project.projectAdapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import kotlinx.coroutines.NonDisposableHandle.parent
import ru.steelwave.steelwave.databinding.ItemProjectBinding
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.unonew.utils.convertLongToDate


class ProjectAdapter: ListAdapter<ProjectModel, ProjectAdapterViewHolder>(ProjectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectBinding.inflate(inflater, parent, false)
        return ProjectAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectAdapterViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvNameProject.text = item.name
            tvRelease.text = convertLongToDate(item.dateRelease)
            tvIncome.text = "$" + item.income.toString()
            tvVisitors.text = item.trafic.toString() + " ч/c"
//            ivProject.setImageBitmap(item.previewImage)
            btnEditProject.setOnClickListener {
            }
        }
    }
}