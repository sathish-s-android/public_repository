package com.google.unittestingsample.repoWithCallback

import kotlinx.coroutines.delay

class RepositoryCallback(val communicator: DataCommunicator,val localRepositoryCallback :LocalBaseRepo,val networkRepositoryCallback :NetworkBaseRepo):BaseRepo() {

    override suspend fun executeForLocal() {
        communicator.loading()
        delay(1000)
        localRepositoryCallback.execute()
    }

    override suspend fun executeForNetwork() {
        communicator.loading()
        delay(3000)
        networkRepositoryCallback.execute()
    }
}

abstract class BaseRepo(){
    abstract suspend fun executeForLocal()
    abstract suspend fun executeForNetwork()
}

interface DataCommunicator{
    fun gotData(data: String)
    fun loading()
    fun gotError(error: String)
}