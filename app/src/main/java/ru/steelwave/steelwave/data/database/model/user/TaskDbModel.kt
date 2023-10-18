package ru.steelwave.steelwave.data.database.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val projectId: Int,
    val userId: Int,
    val descr: String,
    val state: Boolean
)
