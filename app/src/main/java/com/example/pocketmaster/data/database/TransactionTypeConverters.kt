package com.example.pocketmaster.data.database

import androidx.room.TypeConverter
import com.example.pocketmaster.data.model.TransactionTypes

class TransactionTypeConverter {

    @TypeConverter
    fun fromTransactionType(type: TransactionTypes?): String? {
        return type?.name
    }

    @TypeConverter
    fun toTransactionType(value: String?): TransactionTypes? {
        return value?.let { TransactionTypes.valueOf(it) }
    }
}
