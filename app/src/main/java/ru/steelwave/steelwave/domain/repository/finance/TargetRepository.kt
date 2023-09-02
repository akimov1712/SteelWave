package ru.steelwave.steelwave.domain.repository.finance

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.domain.entity.project.ProjectModel

interface TargetRepository {

    suspend fun addTargetUseCase(target: TargetModel)
    fun getAllTargetUseCase(): LiveData<List<TargetModel>>

}