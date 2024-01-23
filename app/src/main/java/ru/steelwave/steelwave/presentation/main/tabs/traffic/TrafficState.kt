package ru.steelwave.steelwave.presentation.main.traffic

import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.entity.traffic.TransferModel
import ru.steelwave.steelwave.domain.entity.traffic.VisitionModel

sealed class TrafficState {

    data object Loading : TrafficState()
    data class ProjectItem(val projectItem: ProjectModel) : TrafficState()
    data class VisitionItem(val visitionItem: VisitionModel) : TrafficState()
    data class TransferList(val transferList: List<TransferModel> = emptyList()) : TrafficState()

}
