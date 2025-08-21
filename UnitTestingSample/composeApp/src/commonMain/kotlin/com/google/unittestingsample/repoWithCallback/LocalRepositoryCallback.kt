package com.google.unittestingsample.repoWithCallback

class LocalRepositoryCallback(val communicator: DataCommunicator):LocalBaseRepo() {

    companion object{
        fun getInstance(communicator: DataCommunicator): LocalRepositoryCallback {
            return LocalRepositoryCallback(communicator)
        }
    }

    override fun execute() {
        communicator.gotData("Local data fetched successfully")
    }
}


abstract class LocalBaseRepo(){
    abstract fun execute()
}
