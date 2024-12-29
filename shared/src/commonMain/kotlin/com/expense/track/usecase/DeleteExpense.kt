package com.expense.track.usecase

import com.expense.track.bussinessObjects.Expense
import com.expense.track.data.DataSource

class DeleteExpense(private val dataSource: DataSource):BaseUseCase<DeleteExpense.Request,Boolean>() {

    data class Request(val expense: Expense)

    override suspend fun execute(parameter: Request):Boolean {
        return dataSource.deleteExpense(parameter.expense).getDateElseThrow()
    }

}
