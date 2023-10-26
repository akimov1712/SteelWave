package ru.steelwave.steelwave.data.repository.ads

import androidx.lifecycle.LiveData
import ru.steelwave.steelwave.domain.entity.ads.DetailsPartnerModel
import ru.steelwave.steelwave.domain.entity.ads.PartnerModel
import ru.steelwave.steelwave.domain.repository.ads.AdsRepository
import javax.inject.Inject

class AdsRepositoryImpl @Inject constructor(): AdsRepository {

    override fun getPartnerList(projectId: Int): LiveData<List<PartnerModel>> {
        TODO("Not yet implemented")
    }

    override fun getDetailPartner(partnerId: Int): DetailsPartnerModel {
        TODO("Not yet implemented")
    }
}