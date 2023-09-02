package ru.steelwave.steelwave.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.steelwave.steelwave.data.database.model.ProjectDbModel
import ru.steelwave.steelwave.data.database.model.UserDbModel

class ProjectConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): ProjectDbModel {
        return gson.fromJson(value, ProjectDbModel::class.java)
    }

    @TypeConverter
    fun toString(userDbModel: ProjectDbModel): String {
        return gson.toJson(userDbModel)
    }

}