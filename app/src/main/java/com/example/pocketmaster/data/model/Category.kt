package com.example.pocketmaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pocketmaster.data.database.TransactionTypeConverters

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val name: String,
    @TypeConverters(TransactionTypeConverters::class)
    val type: TransactionTypes,
    val color: Int
)
