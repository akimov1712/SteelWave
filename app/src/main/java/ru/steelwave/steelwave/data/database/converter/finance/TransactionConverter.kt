package ru.steelwave.steelwave.data.database.converter.finance

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.steelwave.steelwave.data.database.model.finance.TransactionDbModel

class TransactionConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): TransactionDbModel {
        return gson.fromJson(value, TransactionDbModel::class.java)
    }

    @TypeConverter
    fun toString(userDbModel: TransactionDbModel): String {
        return gson.toJson(userDbModel)
    }


}