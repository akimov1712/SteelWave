package ru.steelwave.steelwave.data.mapper.traffic

import ru.steelwave.steelwave.data.database.model.traffic.VisitionDbModel
import ru.steelwave.steelwave.domain.entity.traffic.VisitionModel
import java.util.Date
import javax.inject.Inject

class VisitionMapper @Inject constructor() {

    fun mapEntityToDbModel(visition: VisitionModel) =
        VisitionDbModel(
            id = visition.id,
            projectId = visition.projectId,
            date = visition.date.time,
            totalVisition = visition.totalVisition,
            viewPages = visition.viewPages,
            visits15Seconds = visition.visits15Seconds,
            viewPagesInVisit = visition.viewPagesInVisit,
            averageTime = visition.averageTime
        )

    fun mapDbModelToEntity(visition: VisitionDbModel) =
        VisitionModel(
            id = visition.id,
            projectId = visition.projectId,
            date = Date(visition.date),
            totalVisition = visition.totalVisition,
            viewPages = visition.viewPages,
            visits15Seconds = visition.visits15Seconds,
            viewPagesInVisit = visition.viewPagesInVisit,
            averageTime = visition.averageTime
        )

    fun mapListDbModelToListEntity(visitionList: List<VisitionDbModel>) = visitionList.map {
        mapDbModelToEntity(it)
    }

    fun mapListEntityToListDbModel(visitionList: List<VisitionModel>) = visitionList.map {
        mapEntityToDbModel(it)
    }

}