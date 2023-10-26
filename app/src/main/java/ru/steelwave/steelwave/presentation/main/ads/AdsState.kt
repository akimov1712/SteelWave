package ru.steelwave.steelwave.presentation.main.ads

import ru.steelwave.steelwave.domain.entity.project.ProjectModel

sealed class AdsState{

    class ProjectItem(val project: ProjectModel): AdsState()

}
