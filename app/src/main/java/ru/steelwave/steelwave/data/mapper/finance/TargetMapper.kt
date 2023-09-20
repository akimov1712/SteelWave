package ru.steelwave.steelwave.data.mapper.finance

import ru.steelwave.steelwave.data.database.model.finance.TargetDbModel
import ru.steelwave.steelwave.domain.entity.finance.TargetModel
import javax.inject.Inject

class TargetMapper @Inject constructor(
){

    fun mapEntityToDbModel(target: TargetModel) = TargetDbModel(
        id = target.id,
        projectId = target.projectId,
        name = target.name,
        collectedPrice = target.collectedPrice,
        totalPrice = target.totalPrice,
        isFinished = target.isFinished
    )

    fun mapDbModelToEntity(target: TargetDbModel) = TargetModel(
        id = target.id,
        projectId = target.projectId,
        name = target.name,
        collectedPrice = target.collectedPrice,
        totalPrice = target.totalPrice,
        isFinished = target.isFinished
    )

    fun mapListEntityToListDbModel(targetList: List<TargetModel>) = targetList.map {
        mapEntityToDbModel(it)
    }

    fun mapDbModelToEntity(targetList: List<TargetDbModel>) = targetList.map {
        mapDbModelToEntity(it)
    }


}