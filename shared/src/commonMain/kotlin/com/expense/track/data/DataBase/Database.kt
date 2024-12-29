package com.expense.track.data.DataBase

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import androidx.sqlite.SQLiteConnection
import com.expense.track.bussinessObjects.Category
import com.expense.track.bussinessObjects.Expense
import com.expense.track.bussinessObjects.OrderBy
import kotlinx.coroutines.flow.Flow

@Database(entities = [Expense::class,Category::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): ExpenseDao
}


@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(item: Expense)

    @Delete
    suspend fun deleteExpense(item: Expense)

    @Update
    suspend fun update(item: Expense)

    @Query("SELECT count(*) FROM Expense")
    suspend fun count(): Int

    @Query("SELECT * FROM Expense ORDER BY spendTime ASC")
    fun getAllAsFlowASC(): Flow<List<Expense>>

    @Query("SELECT * FROM Expense ORDER BY spendTime ASC")
    suspend fun getAllASC(): List<Expense>

    @Query("SELECT * FROM Expense Where spendTime >= :fromDate AND spendTime <= :untilDate ORDER BY spendTime ASC")
    suspend fun getAllBetweenDatesASC(fromDate:Long, untilDate:Long): List<Expense>

    @Query("SELECT * FROM Expense ORDER BY spendTime DESC")
    fun getAllAsFlowDESC(): Flow<List<Expense>>

    @Query("SELECT * FROM Expense ORDER BY spendTime DESC")
    suspend fun getAllDESC(): List<Expense>

    @Query("SELECT * FROM Expense Where spendTime >= :fromDate AND spendTime <= :untilDate ORDER BY spendTime DESC")
    suspend fun getAllBetweenDatesDESC(fromDate:Long, untilDate:Long): List<Expense>

    @Query("SELECT * FROM Category")
    suspend fun getAllCategory(): List<Category>

    @Insert
    suspend fun insertCategory(item: Category)
}

