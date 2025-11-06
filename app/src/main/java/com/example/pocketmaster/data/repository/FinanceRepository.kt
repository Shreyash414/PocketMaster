package com.example.pocketmaster.data.repository

import com.example.pocketmaster.data.dao.CategoryDao
import com.example.pocketmaster.data.dao.TransactionDao
import com.example.pocketmaster.data.model.Category
import com.example.pocketmaster.data.model.Transaction
import com.example.pocketmaster.data.model.TransactionTypes
import kotlinx.coroutines.flow.Flow

class FinanceRepository(private val transactiondao: TransactionDao, private val categorydao: CategoryDao) {

    // Transaction functions
    fun getAllTransactions(): Flow<List<Transaction>> = transactiondao.getAllTransactions()
    fun getTransactionById(id: Int): Flow<Transaction> = transactiondao.getTransactionById(id)
    fun getTransactionsByType(type: TransactionTypes): Flow<List<Transaction>> = transactiondao.getTransactionsByType(type)
    fun getTotalAmount(): Flow<Double?> = transactiondao.getTotalAmount()
    suspend fun getTotalByType(type: TransactionTypes)=transactiondao.getTotalByType(type) ?: 0.0
    suspend fun insertTransaction(transaction: Transaction) = transactiondao.insert(transaction)
    suspend fun updateTransaction(transaction: Transaction) = transactiondao.update(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = transactiondao.delete(transaction)

    // Category functions
    fun getAllCategories(): Flow<List<Category>> = categorydao.getAllCategories()
    suspend fun insertCategory(category: Category) = categorydao.insert(category)
}
