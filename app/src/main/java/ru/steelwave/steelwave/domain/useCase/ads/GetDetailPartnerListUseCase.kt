package ru.steelwave.steelwave.domain.useCase.ads

import ru.steelwave.steelwave.domain.repository.ads.AdsRepository
import javax.inject.Inject

class GetDetailPartnerListUseCase @Inject constructor(
    private val repository: AdsRepository
){

    operator fun invoke(partnerId: Int) = repository.getDetailPartner(partnerId)

}