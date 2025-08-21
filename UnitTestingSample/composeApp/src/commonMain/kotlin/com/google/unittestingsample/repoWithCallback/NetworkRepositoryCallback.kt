package com.google.unittestingsample.repoWithCallback

class NetworkRepositoryCallback(val communicator: DataCommunicator): NetworkBaseRepo(){

    companion object{
        fun getInstance(communicator: DataCommunicator): NetworkRepositoryCallback {
            return NetworkRepositoryCallback(communicator)
        }
    }

    override fun execute() {
        communicator.gotData("Network data fetched successfully")
    }
}


abstract class NetworkBaseRepo(){
    abstract fun execute()
}