package com.expense.track.data

import com.expense.track.bussinessObjects.Category
import com.expense.track.bussinessObjects.Expense
import com.expense.track.bussinessObjects.OrderBy
import com.expense.track.usecase.Result

interface DataSource {
    suspend fun getAllExpense(fromDate:Long, untilDate:Long,sortBy: OrderBy):Result<List<Expense>>
    suspend fun insertExpense(expense: Expense):Unit
    suspend fun deleteExpense(expense: Expense):Result<Boolean>
    suspend fun updateExpense(expense: Expense):Unit
    suspend fun getAllCategory(): Result<List<Category>>
    suspend fun insertCategory(category: Category):Unit
}