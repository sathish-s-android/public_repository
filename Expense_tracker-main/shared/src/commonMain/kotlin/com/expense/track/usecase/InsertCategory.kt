package com.expense.track.usecase

import com.expense.track.bussinessObjects.Category
import com.expense.track.data.DataSource


class InsertCategory(private val dataSource: DataSource):BaseUseCase<InsertCategory.Request,Unit>() {
    data class Request(val category: Category)

    override suspend fun execute(parameter: Request) {
        dataSource.insertCategory(parameter.category)
    }
}