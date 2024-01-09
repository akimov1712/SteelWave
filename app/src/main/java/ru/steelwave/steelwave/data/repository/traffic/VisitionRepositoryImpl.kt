package ru.steelwave.steelwave.data.repository.traffic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.steelwave.steelwave.data.database.dao.traffic.VisitionDao
import ru.steelwave.steelwave.data.mapper.traffic.VisitionMapper
import ru.steelwave.steelwave.domain.entity.traffic.VisitionModel
import ru.steelwave.steelwave.domain.repository.traffic.VisitionRepository
import java.util.Date
import javax.inject.Inject

class VisitionRepositoryImpl @Inject constructor(
    private val mapper: VisitionMapper,
    private val dao: VisitionDao
) : VisitionRepository {

    override fun getVisitionUseCase(projectId: Int, date: Date): Flow<VisitionModel> {
        return dao.getVisition(projectId, date.time).map {
            mapper.mapDbModelToEntity(it)
        }
    }
}