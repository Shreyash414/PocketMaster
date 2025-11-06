package com.example.pocketmaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pocketmaster.data.database.TransactionTypeConverters

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val description: String,
    val category: String,
    @TypeConverters(TransactionTypeConverters::class)
    val type: TransactionTypes,
    val date: Long = System.currentTimeMillis()
)
