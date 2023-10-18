package ru.steelwave.steelwave.domain.entity.user

import ru.steelwave.steelwave.Const

data class TaskModel(
    val id: Int = Const.UNDEFINED_ID,
    val projectId: Int,
    val userId: Int,
    val descr: String,
    val state: Boolean = false
)
