package ru.steelwave.steelwave.data.database.model.ads

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.steelwave.steelwave.Const
import java.util.Date

@Entity(tableName = "partners")
data class PartnerDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val projectId: Int,
    val name: String,
    val preview: Bitmap?,
    val contractEndDate: Long
)
