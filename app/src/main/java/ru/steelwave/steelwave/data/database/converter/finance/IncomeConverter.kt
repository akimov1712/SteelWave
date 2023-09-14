package ru.steelwave.steelwave.data.database.converter.finance

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.steelwave.steelwave.data.database.model.finance.IncomeDbModel
import ru.steelwave.steelwave.data.database.model.finance.TransactionDbModel

class IncomeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): IncomeDbModel {
        return gson.fromJson(value, IncomeDbModel::class.java)
    }

    @TypeConverter
    fun toString(userDbModel: IncomeDbModel): String {
        return gson.toJson(userDbModel)
    }


}