package com.expense.track.usecase

import com.expense.track.bussinessObjects.Expense
import com.expense.track.data.DataSource

class InsertExpense(private val dataSource: DataSource):BaseUseCase<InsertExpense.Request,Unit>() {

    data class Request(val expense: Expense)

    override suspend fun execute(parameter: Request) {
        dataSource.insertExpense(parameter.expense)
    }

}