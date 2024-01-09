package ru.steelwave.steelwave.domain.repository.finance

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.steelwave.steelwave.domain.entity.finance.TargetModel

interface TargetRepository {

    suspend fun addTarget(target: TargetModel)
    fun getAllTarget(projectId: Int): Flow<List<TargetModel>>
    suspend fun getTargetItem(targetId: Int): TargetModel
    suspend fun deleteTarget(targetId: Int)

}