package ru.steelwave.steelwave.domain.repository.finance

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.finance.TargetModel

interface TargetRepository {

    suspend fun addTargetUseCase(target: TargetModel)
    fun getAllTargetUseCase(projectId: Int): LiveData<List<TargetModel>>

}