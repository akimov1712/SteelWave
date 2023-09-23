package ru.steelwave.steelwave.domain.repository.traffic

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.traffic.TransferModel
import ru.steelwave.steelwave.domain.entity.traffic.VisitionModel
import java.util.Date

interface VisitionRepository {

    fun getVisitionUseCase(projectId: Int, date: Date): LiveData<VisitionModel>

}