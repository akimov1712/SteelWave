package ru.steelwave.steelwave.presentation.main.project

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProjectViewModelFactory(private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectViewModel::class.java)){
            return ProjectViewModel(application) as T
        }
        throw RuntimeException("Unknown view model class: $modelClass")
    }
}