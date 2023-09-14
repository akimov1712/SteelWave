package ru.steelwave.steelwave.data.database.converter.finance

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.steelwave.steelwave.data.database.model.finance.YearIncomeDbModel

class YearIncomeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): YearIncomeDbModel {
        return gson.fromJson(value, YearIncomeDbModel::class.java)
    }

    @TypeConverter
    fun toString(userDbModel: YearIncomeDbModel): String {
        return gson.toJson(userDbModel)
    }


}