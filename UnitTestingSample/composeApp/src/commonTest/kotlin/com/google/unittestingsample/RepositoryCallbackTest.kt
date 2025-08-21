package com.google.unittestingsample

import com.google.unittestingsample.repoWithCallback.DataCommunicator
import com.google.unittestingsample.repoWithCallback.LocalBaseRepo
import com.google.unittestingsample.repoWithCallback.NetworkBaseRepo
import com.google.unittestingsample.repoWithCallback.RepositoryCallback
import dev.mokkery.MokkeryCompilerDefaults
import dev.mokkery.annotations.InternalMokkeryApi
import dev.mokkery.answering.calls
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class RepositoryCallbackTest {

    val scope = TestScope()

    @OptIn(InternalMokkeryApi::class)
    val communicator = mock<DataCommunicator>{}

    @BeforeTest
    fun setup() {
        // Define the behavior of the gotData method
        every { communicator.gotData(any()) } returns Unit // Or just runs if you don't need a specific return
        every { communicator.loading() } returns Unit // Or just runs if you don't need a specific return
        every { communicator.gotError(any()) } returns Unit // Or just runs if you don't need a specific return
    }

    val localRepositoryCallback = mock<LocalBaseRepo> {
        every { execute() } calls {
            communicator.gotData("Local data fetched successfully")
        }
    }
    val networkRepositoryCallback = mock<NetworkBaseRepo> {
        every { execute() } calls {
            communicator.gotData("Network data fetched successfully")
        }
    }

    val repositoryCallback = RepositoryCallback(communicator,localRepositoryCallback,networkRepositoryCallback)

    @Test
    fun `test case for local set of data`()= scope.runTest{
        repositoryCallback.executeForLocal()

        verify {
            communicator.loading()
        }
        verify {
            communicator.gotData("Local data fetched successfully")
        }
    }

    @Test
    fun `test case for network set of data`()= scope.runTest{
        repositoryCallback.executeForNetwork()

        verify {
            communicator.loading()
        }
        verify {
            communicator.gotData("Network data fetched successfully")
        }
    }
}