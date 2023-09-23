package ru.steelwave.steelwave.data.database.converter.traffic

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.steelwave.steelwave.data.database.model.project.ProjectDbModel
import ru.steelwave.steelwave.data.database.model.traffic.TransferDbModel
import ru.steelwave.steelwave.data.database.model.traffic.VisitionDbModel

class VisitionConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): VisitionDbModel {
        return gson.fromJson(value, VisitionDbModel::class.java)
    }

    @TypeConverter
    fun toString(userDbModel: VisitionDbModel): String {
        return gson.toJson(userDbModel)
    }

}