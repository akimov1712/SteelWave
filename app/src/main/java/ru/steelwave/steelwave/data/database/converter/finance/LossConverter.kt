package ru.steelwave.steelwave.data.database.converter.finance

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.steelwave.steelwave.data.database.model.finance.IncomeDbModel
import ru.steelwave.steelwave.data.database.model.finance.LossDbModel
import ru.steelwave.steelwave.data.database.model.finance.TransactionDbModel

class LossConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): LossDbModel {
        return gson.fromJson(value, LossDbModel::class.java)
    }

    @TypeConverter
    fun toString(userDbModel: LossDbModel): String {
        return gson.toJson(userDbModel)
    }


}