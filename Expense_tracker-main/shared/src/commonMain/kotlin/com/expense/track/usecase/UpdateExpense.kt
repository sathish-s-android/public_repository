package com.expense.track.usecase

import com.expense.track.bussinessObjects.Expense
import com.expense.track.data.DataSource

class UpdateExpense(private val dataSource: DataSource):BaseUseCase<UpdateExpense.Request,Unit>() {
    data class Request(val expense: Expense)

    override suspend fun execute(parameter: Request) {
        dataSource.updateExpense(parameter.expense)
    }
}
