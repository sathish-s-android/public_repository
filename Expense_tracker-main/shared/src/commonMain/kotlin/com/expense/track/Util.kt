package com.expense.track

import com.expense.track.data.DataBase.MyDataBase
import com.expense.track.data.DataSource
import com.expense.track.data.Repository
import com.expense.track.data.local.LocalDataSource
import com.expense.track.data.local.LocalRepository
import com.expense.track.usecase.DeleteExpense
import com.expense.track.usecase.GetAllCategory
import com.expense.track.usecase.GetExpense
import com.expense.track.usecase.InsertCategory
import com.expense.track.usecase.InsertExpense
import com.expense.track.usecase.UpdateExpense


object DependencyProvider{
    fun provideGetExpense():GetExpense{
        return GetExpense(getDataSource())
    }

    fun provideInsertExpense(): InsertExpense {
        return InsertExpense(getDataSource())
    }

    fun provideUpdateExpense(): UpdateExpense {
        return UpdateExpense(getDataSource())
    }

    fun provideGetAllCategory(): GetAllCategory {
        return GetAllCategory(getDataSource())
    }

    fun provideDeleteExpense(): DeleteExpense {
        return DeleteExpense(getDataSource())
    }

    fun provideInsertCategory(): InsertCategory {
        return InsertCategory(getDataSource())
    }

    private fun getDataSource():DataSource{
        return Repository.getInstance(getLocalDataSource())
    }

    private fun getLocalDataSource():LocalDataSource{
        return LocalRepository.getInstance(MyDataBase.getDataBase())
    }
}

