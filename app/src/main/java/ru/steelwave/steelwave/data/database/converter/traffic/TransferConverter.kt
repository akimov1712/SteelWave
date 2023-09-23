package ru.steelwave.steelwave.data.database.converter.traffic

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.steelwave.steelwave.data.database.model.project.ProjectDbModel
import ru.steelwave.steelwave.data.database.model.traffic.TransferDbModel

class TransferConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): TransferDbModel {
        return gson.fromJson(value, TransferDbModel::class.java)
    }

    @TypeConverter
    fun toString(userDbModel: TransferDbModel): String {
        return gson.toJson(userDbModel)
    }

}