package com.expense.track.data.local

import com.expense.track.bussinessObjects.Category
import com.expense.track.bussinessObjects.Expense
import com.expense.track.bussinessObjects.OrderBy
import com.expense.track.data.DataBase.AppDatabase
import com.expense.track.data.DataBase.ExpenseDao
import com.expense.track.usecase.Result

class LocalRepository(val dataBase:AppDatabase):LocalDataSource {

    companion object{
        private lateinit var instance: LocalDataSource
        private lateinit var dao: ExpenseDao
        fun getInstance(dataBase:AppDatabase): LocalDataSource {
            return if (!::instance.isInitialized){
                instance = LocalRepository(dataBase)
                dao = dataBase.getDao()
                instance
            }else{
                instance
            }
        }
    }


    override suspend fun getAllExpeneses(fromDate:Long, untilDate:Long,sortBy: OrderBy): Result<List<Expense>> {
        return try {

            if (fromDate == 0L || untilDate == 0L){
                when(sortBy){
                    OrderBy.ASC ->{
                        Result.Sucess(dao.getAllASC())
                    }
                    OrderBy.DESC ->{
                        Result.Sucess(dao.getAllDESC())
                    }
                }

            }else{
                when(sortBy){
                    OrderBy.ASC ->{
                        Result.Sucess(dao.getAllBetweenDatesASC(fromDate, untilDate))
                    }
                    OrderBy.DESC ->{
                        Result.Sucess(dao.getAllBetweenDatesDESC(fromDate, untilDate))
                    }
                }
            }

        }catch (e:Exception){
            Result.Failure(e.message?:"")
        }

    }

    override suspend fun insertExpense(expense: Expense) {
        try {
            dao.insert(expense)
        }catch (E:Exception){

        }
    }

    override suspend fun deleteExpense(expense: Expense):Result<Boolean> {
        return try {
            dao.deleteExpense(expense)
            Result.Sucess(true)
        }catch (E:Exception){
            Result.Failure("Not able to delete due to : ${E.message}")
        }
    }

    override suspend fun updateExpense(expense: Expense) {
        try {
            dao.update(expense)
        }catch (E:Exception){

        }
    }

    override suspend fun getAllCategory(): Result<List<Category>> {
        return try {
            Result.Sucess(dao.getAllCategory())
        }catch (e:Exception){
            Result.Failure(e.message?:"")
        }
    }

    override suspend fun insertCategory(expense: Category) {
        try {
            dao.insertCategory(expense)
        }catch (E:Exception){

        }
    }
}