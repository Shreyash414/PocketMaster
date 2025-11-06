package com.example.pocketmaster.data.database

import android.content.Context
import android.graphics.Color
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pocketmaster.data.dao.CategoryDao
import com.example.pocketmaster.data.dao.TransactionDao
import com.example.pocketmaster.data.model.Category
import com.example.pocketmaster.data.model.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.room.TypeConverters
import com.example.pocketmaster.data.model.TransactionTypes

@Database(
    entities = [
        Transaction::class,
        Category::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(TransactionTypeConverter::class)  // ← Fixed: Put on separate line
abstract class AppDatabase: RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "finance_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Add default categories
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    val categoryDao = database.categoryDao()

                                    // default expense categories
                                    categoryDao.insert(Category(name = "Food & Dining", type = TransactionTypes.EXPENSE, color = Color.parseColor("#FF5252")))
                                    categoryDao.insert(Category(name = "Transportation", type = TransactionTypes.EXPENSE, color = Color.parseColor("#448AFF")))
                                    categoryDao.insert(Category(name = "Shopping", type = TransactionTypes.EXPENSE, color = Color.parseColor("#4CAF50")))
                                    categoryDao.insert(Category(name = "Bills & Utilities", type = TransactionTypes.EXPENSE, color = Color.parseColor("#FFC107")))
                                    categoryDao.insert(Category(name = "Healthcare", type = TransactionTypes.EXPENSE, color = Color.parseColor("#E840F8")))

                                    // default income categories
                                    categoryDao.insert(Category(name = "Salary", type = TransactionTypes.INCOME, color = Color.parseColor("#00BCD4")))
                                    categoryDao.insert(Category(name = "Freelance", type = TransactionTypes.INCOME, color = Color.parseColor("#9C27B0")))
                                    categoryDao.insert(Category(name = "Investments", type = TransactionTypes.INCOME, color = Color.parseColor("#4CAF50")))
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}