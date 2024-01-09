package ru.steelwave.steelwave.presentation.main.ads

import ru.steelwave.steelwave.domain.entity.project.ProjectModel

sealed class AdsState{

    data object Loading: AdsState()
    data class ProjectItem(val project: ProjectModel): AdsState()

}
