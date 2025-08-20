package com.google.unittestingsample.Repo

class LocalRepository():LocalDataSource {
    override suspend fun getData(): String {
        return "Dummy Local Data"
    }
}


interface LocalDataSource {
    suspend fun getData(): String
}