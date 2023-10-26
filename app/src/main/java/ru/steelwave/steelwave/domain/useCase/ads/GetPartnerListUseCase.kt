package ru.steelwave.steelwave.domain.useCase.ads

import ru.steelwave.steelwave.domain.repository.ads.AdsRepository
import javax.inject.Inject

class GetPartnerListUseCase @Inject constructor(
    private val repository: AdsRepository
){

    operator fun invoke(projectId: Int) = repository.getPartnerList(projectId)

}