package com.google.unittestingsample

import com.google.unittestingsample.Repo.LocalDataSource
import com.google.unittestingsample.Repo.NetWorkDataSource
import com.google.unittestingsample.Repo.Repository
import dev.mokkery.answering.calls
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class RepoTest {

    val lRepository = mock<LocalDataSource> {
        everySuspend { getData() } calls {  "mock local data" }
    }

    val nRepository = mock<NetWorkDataSource> {
        everySuspend { getData() } calls {  "mock local data" }
    }

    val repository = Repository(lRepository, nRepository)

    @Test
    fun testRepo() =  runBlocking{
        val expetedValue = "mock local data"
        val actualValue = repository.getDataFromLocal()
        assertEquals(expetedValue,actualValue)
    }
    
}