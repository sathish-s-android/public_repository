package com.expense.track.data.DataBase

object MyDataBase{
    private var dataBase: AppDatabase? = null

    internal fun setDataBase(appDatabase: AppDatabase){
        dataBase = appDatabase
    }

    internal fun getDataBase(): AppDatabase {
        return dataBase!!
    }
}
