package ru.steelwave.steelwave.presentation.main.project


sealed class ProjectState{

    object ErrorInputName : ProjectState()
    object ErrorImage : ProjectState()
    object ShouldCloseScreen : ProjectState()

}
