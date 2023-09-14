package ru.steelwave.steelwave.data.database.converter.finance

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.steelwave.steelwave.data.database.model.finance.TransactionDbModel

class TransactionListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<TransactionDbModel> {
        val listType = object : TypeToken<List<TransactionDbModel>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun toString(transactionList: List<TransactionDbModel>): String {
        return gson.toJson(transactionList)
    }
}