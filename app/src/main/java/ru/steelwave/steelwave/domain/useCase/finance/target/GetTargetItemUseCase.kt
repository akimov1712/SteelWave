package ru.steelwave.steelwave.domain.useCase.finance.target

import ru.steelwave.steelwave.domain.repository.finance.TargetRepository
import javax.inject.Inject

class GetTargetItemUseCase @Inject constructor(
    private val repository: TargetRepository
){

    suspend operator fun invoke(targetId: Int) = repository.getTargetItem(targetId)

}