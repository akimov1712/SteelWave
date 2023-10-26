package ru.steelwave.steelwave.domain.repository.ads

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.ads.DetailsPartnerModel
import ru.steelwave.steelwave.domain.entity.ads.PartnerModel

interface AdsRepository {

    fun getPartnerList(projectId: Int): LiveData<List<PartnerModel>>
    fun getDetailPartner(partnerId: Int): DetailsPartnerModel

}