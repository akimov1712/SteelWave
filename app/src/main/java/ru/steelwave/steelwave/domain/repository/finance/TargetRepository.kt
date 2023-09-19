package ru.steelwave.steelwave.domain.repository.finance

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.finance.TargetModel

interface TargetRepository {

    suspend fun addTarget(target: TargetModel)
    fun getAllTarget(projectId: Int): LiveData<List<TargetModel>>
    suspend fun getTargetItem(targetId: Int): TargetModel
    suspend fun deleteTarget(targetId: Int)

}