package ru.steelwave.steelwave.data.repository.finance

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.steelwave.steelwave.Loger
import ru.steelwave.steelwave.data.database.dao.finance.LossDao
import ru.steelwave.steelwave.data.mapper.finance.LossMapper
import ru.steelwave.steelwave.domain.entity.finance.LossModel
import ru.steelwave.steelwave.domain.repository.finance.LossRepository
import java.sql.Date
import javax.inject.Inject

class LossRepositoryImpl @Inject constructor(
    private val dao: LossDao,
    private val mapper: LossMapper
): LossRepository {

    override suspend fun addLossUseCase(loss: LossModel) {
        dao.addLoss(mapper.mapEntityToDbModel(loss))
    }

    override suspend fun getLossUseCase(projectId: Int, lossDate: Date): LossModel? {
        val date = lossDate.time
        return mapper.mapDbModelToEntity(dao.getLoss(projectId, date))
    }
}