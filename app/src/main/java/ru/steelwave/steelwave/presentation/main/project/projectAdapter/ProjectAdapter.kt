package ru.steelwave.steelwave.presentation.main.project.projectAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.steelwave.steelwave.databinding.ItemProjectBinding
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.unonew.utils.convertLongToDate


class ProjectAdapter: ListAdapter<ProjectModel, ProjectAdapterViewHolder>(ProjectDiffCallback()) {

    var onClickEditProjectListener: ((ProjectModel) -> Unit)? = null

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
            ivProject.setImageBitmap(item.previewImage)
            if (item.teamLead == null){
                tvTeamLead.text = "Не назначен"
            } else {
                tvTeamLead.text = item.teamLead!!.firstName + item.teamLead!!.lastName
            }
            btnEditProject.setOnClickListener {
                onClickEditProjectListener?.invoke(item)
            }
        }
    }

}