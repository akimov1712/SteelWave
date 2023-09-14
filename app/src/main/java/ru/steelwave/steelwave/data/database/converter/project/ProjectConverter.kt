package ru.steelwave.steelwave.data.database.converter.project

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.steelwave.steelwave.data.database.model.project.ProjectDbModel

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