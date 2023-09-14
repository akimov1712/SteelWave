package ru.steelwave.steelwave.data.database.converter.user

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.steelwave.steelwave.data.database.model.user.UserDbModel

class UserConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): UserDbModel {
        return gson.fromJson(value, UserDbModel::class.java)
    }

    @TypeConverter
    fun toString(userDbModel: UserDbModel): String {
        return gson.toJson(userDbModel)
    }

}