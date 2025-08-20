package com.google.unittestingsample.Repo

class NetworkRepository:NetWorkDataSource {
    override suspend fun getData(): String {
        return "Dummy Network Data"
    }
}




interface NetWorkDataSource {
    suspend fun getData(): String
}
