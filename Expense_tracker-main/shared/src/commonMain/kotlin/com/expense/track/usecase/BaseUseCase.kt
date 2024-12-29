package com.expense.track.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in P,R>(val dispatcher: CoroutineDispatcher = Dispatchers.IO){
    suspend operator fun invoke(parameter: P):Result<R>{
        return try{
            withContext(dispatcher){
                Result.Sucess(execute(parameter))
            }
        }catch (e:Exception){
            Result.Failure(e.message?:"")
        }
    }

    protected abstract suspend fun execute(parameter: P):R
}



sealed class Result<out L>(){
    class Sucess<out L>(val data:L):Result<L>()

    class Failure(val error: String):Result<Nothing>()
}

fun <T>Result<T>.getDateElseThrow():T{
    return when(this){
        is Result.Sucess->{
            data
        }
        is Result.Failure ->{
            throw Exception(error)
        }
    }
}

fun <T>Result<T>.getDateElse(value:T,error:(String)->Unit):T{
    return when(this){
        is Result.Sucess->{
            data
        }
        is Result.Failure ->{
            error(this.error)
            value
        }
    }
}
