package com.expense.track.usecase

import com.expense.track.bussinessObjects.Category
import com.expense.track.data.DataSource

class GetAllCategory(private val dataSource: DataSource) :BaseUseCase<GetAllCategory.Request,List<Category>>() {
    data class Request(val nothing:Nothing?=null)
    override suspend fun execute(parameter: GetAllCategory.Request):List<Category> {
        return dataSource.getAllCategory().getDateElseThrow()
    }
}
