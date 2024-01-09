package ru.steelwave.steelwave.data.repository.finance

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.steelwave.steelwave.data.database.dao.finance.TargetDao
import ru.steelwave.steelwave.data.mapper.finance.TargetMapper
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import ru.steelwave.steelwave.domain.repository.finance.TargetRepository
import javax.inject.Inject

class TargetRepositoryImpl @Inject constructor(
    private val dao: TargetDao,
    private val mapper: TargetMapper
) : TargetRepository {

    override suspend fun addTarget(target: TargetModel) {
        dao.addTarget(mapper.mapEntityToDbModel(target))
    }

    override fun getAllTarget(projectId: Int): Flow<List<TargetModel>> {
        return dao.getAllTarget(projectId).map {
            mapper.mapDbModelListToEntityList(it)
        }

    }

    override suspend fun getTargetItem(targetId: Int) =
        mapper.mapDbModelToEntity(dao.getTargetItem(targetId))

    override suspend fun deleteTarget(targetId: Int) {
        dao.deleteTarget(targetId)
    }
}