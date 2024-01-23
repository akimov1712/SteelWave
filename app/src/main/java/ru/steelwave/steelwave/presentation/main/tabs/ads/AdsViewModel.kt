package ru.steelwave.steelwave.presentation.main.ads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.steelwave.steelwave.domain.useCase.ads.GetDetailPartnerListUseCase
import ru.steelwave.steelwave.domain.useCase.ads.GetPartnerListUseCase
import ru.steelwave.steelwave.domain.useCase.project.GetProjectUseCase
import javax.inject.Inject

@HiltViewModel
class AdsViewModel @Inject constructor(
    private val getProjectUseCase: GetProjectUseCase,
    private val getPartnerListUseCase: GetPartnerListUseCase,
    private val getDetailPartnerListUseCase: GetDetailPartnerListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AdsState>(AdsState.Loading)
    val state = _state.asStateFlow()

    fun getProjectItem(projectItemId: Int) {
        viewModelScope.launch {
            val item = getProjectUseCase(projectItemId)
            _state.value = AdsState.ProjectItem(item)
        }
    }

}