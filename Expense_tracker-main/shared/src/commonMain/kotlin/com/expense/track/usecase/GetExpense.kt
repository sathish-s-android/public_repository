package com.expense.track.usecase

import com.expense.track.bussinessObjects.Expense
import com.expense.track.bussinessObjects.OrderBy
import com.expense.track.data.DataSource

class GetExpense(private val dataSource: DataSource):BaseUseCase<GetExpense.Request,List<Expense>>() {

    data class Request(val fromDate:Long = 0L,val untilDate:Long = 0L,val sortBy:OrderBy = OrderBy.ASC)

    override suspend fun execute(parameter: Request): List<Expense> {
        return dataSource.getAllExpense(parameter.fromDate,parameter.untilDate, parameter.sortBy).getDateElseThrow()
    }
}
