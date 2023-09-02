package ru.steelwave.unonew.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertLongToTime(ms: Long): String{
    val date = Date(ms)
    val calendar = Calendar.getInstance()
    calendar.time = date
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(calendar.time)
}

fun convertLongToDate(ms: Long): String{
    val date = Date(ms)
    val calendar = Calendar.getInstance()
    calendar.time = date
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
}