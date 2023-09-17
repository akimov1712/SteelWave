package ru.steelwave.steelwave.domain.repository.finance

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.finance.IncomeModel
import java.sql.Date

interface IncomeRepository {

    suspend fun getIncomeUseCase(projectId: Int, incomeDate: Date): IncomeModel?

}