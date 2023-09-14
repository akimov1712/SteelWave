package ru.steelwave.steelwave.data.database.model.project

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var previewImage: Bitmap?,
    val dateRelease: Long,
    var trafic: Int = 0,
    var income: Int = 0
)