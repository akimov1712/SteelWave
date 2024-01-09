package ru.steelwave.steelwave.presentation.main.project


sealed class ProjectState{

    data object ErrorInputName : ProjectState()
    data object ErrorImage : ProjectState()
    data object ShouldCloseScreen : ProjectState()
    data object Loading : ProjectState()

}
