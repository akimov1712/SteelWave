package ru.steelwave.steelwave.data.repository.finance

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.steelwave.steelwave.data.database.dao.finance.TargetDao
import ru.steelwave.steelwave.data.mapper.finance.TargetMapper
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.domain.repository.finance.TargetRepository
import javax.inject.Inject

class TargetRepositoryImpl @Inject constructor(
    private val dao: TargetDao,
    private val mapper: TargetMapper
): TargetRepository{

    override suspend fun addTarget(target: TargetModel) {
        dao.addTarget(mapper.mapEntityToDbModel(target))
    }

    override fun getAllTarget(projectId: Int): LiveData<List<TargetModel>> {
        return Transformations.map(dao.getAllTarget(projectId)){
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun getTargetItem(targetId: Int) =
        mapper.mapDbModelToEntity(dao.getTargetItem(targetId))

    override suspend fun deleteTarget(targetId: Int) {
        dao.deleteTarget(targetId)
    }
}