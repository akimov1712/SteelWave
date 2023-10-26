package ru.steelwave.steelwave.presentation.main.ads

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.domain.entity.project.ProjectModel
import ru.steelwave.steelwave.domain.useCase.ads.GetDetailPartnerListUseCase
import ru.steelwave.steelwave.domain.useCase.ads.GetPartnerListUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import javax.inject.Inject

class AdsViewModel @Inject constructor(
    private val getProjectUseCase: GetProjectUseCase,
    private val getPartnerListUseCase: GetPartnerListUseCase,
    private val getDetailPartnerListUseCase: GetDetailPartnerListUseCase
): ViewModel() {

    private val _state = MutableLiveData<AdsState>()
    private val state: LiveData<AdsState>
        get() = _state

    private val _projectItem = MutableLiveData<ProjectModel>()
    val projectItem: LiveData<ProjectModel>
        get() = _projectItem

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _state.value = AdsState.ProjectItem(item)
        }
    }

}