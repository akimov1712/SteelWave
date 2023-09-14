package ru.steelwave.steelwave.data.database.converter.finance

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.steelwave.steelwave.data.database.model.finance.IncomeDbModel
import ru.steelwave.steelwave.data.database.model.finance.LossDbModel
import ru.steelwave.steelwave.data.database.model.finance.TargetDbModel
import ru.steelwave.steelwave.data.database.model.finance.TransactionDbModel

class TargetConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): TargetDbModel {
        return gson.fromJson(value, TargetDbModel::class.java)
    }

    @TypeConverter
    fun toString(userDbModel: TargetDbModel): String {
        return gson.toJson(userDbModel)
    }


}