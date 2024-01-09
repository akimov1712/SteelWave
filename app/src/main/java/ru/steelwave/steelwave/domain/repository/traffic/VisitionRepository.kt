package ru.steelwave.steelwave.domain.repository.traffic

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.steelwave.steelwave.domain.entity.traffic.TransferModel
import ru.steelwave.steelwave.domain.entity.traffic.VisitionModel
import java.util.Date

interface VisitionRepository {

    fun getVisitionUseCase(projectId: Int, date: Date): Flow<VisitionModel>

}