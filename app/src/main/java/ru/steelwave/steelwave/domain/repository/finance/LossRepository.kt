package ru.steelwave.steelwave.domain.repository.finance

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.finance.LossModel
import java.sql.Date

interface LossRepository {

    suspend fun addLossUseCase(loss: LossModel)
    suspend fun getLossUseCase(lossDate: Date): LossModel

}