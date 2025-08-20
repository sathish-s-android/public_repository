package com.google.unittestingsample.Repo

class Repository(val localDataSource: LocalDataSource, val networkDataSource: NetWorkDataSource):DataSource {
    override suspend fun getDataFromLocal(): String {
        return localDataSource.getData()
    }

    override suspend fun getDataFromNetwork(): String {
        return networkDataSource.getData()
    }
}


interface DataSource {
    suspend fun getDataFromLocal(): String
    suspend fun getDataFromNetwork(): String
}