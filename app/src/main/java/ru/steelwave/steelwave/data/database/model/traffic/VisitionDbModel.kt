package ru.steelwave.steelwave.data.database.model.traffic

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.steelwave.steelwave.Const

@Entity(tableName = "visitions")
data class VisitionDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val projectId: Int,
    val date: Long,
    val totalVisition: Int,
    val viewPages: Int,
    val viewPagesInVisit: Int,
    val visits15Seconds: Int,
    val averageTime: Int
)
