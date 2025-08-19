package com.google.unittestingsample

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ComprehensiveCalculatorTest {

    private val testDispatcher = StandardTestDispatcher()
    val testScope = TestScope(testDispatcher)
    private val calculator = ComprehensiveCalculator()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `add integers`(){
        val a = 2
        val b = 4
        val expected = 6

        // Act
        val actual = calculator.addIntegers(a,b)

        //Assert
        assertEquals(expected,actual, "Expected $expected but got $actual")
    }

    @Test
    fun `add float`(){
        val a = 2.5f
        val b = 4.5f
        val expected = 7.0f

        // Act
        val actual = calculator.addFloats(a,b)

        //Assert
        assertEquals(expected,actual, 0.0001f,"Expected $expected but got $actual")
    }

    @Test
    fun `test increment`(){

        calculator.incrementState()
        val expected = 1

        // Act
        val actual = calculator.getState()

        //Assert
        assertEquals(expected,actual,"Expected $expected but got $actual")
    }

    @Test
    fun `fetch result flow returns expected flow`() = runBlocking {
        // Arrange
        val expected = listOf(1, 2, 3)

        // Act
        val actual = calculator.fetchResultFlow().toList()

        // Assert
        assertContentEquals(expected, actual, "Flow should emit 1, 2, 3")
    }


    @Test
    fun `fetch result async`(){
        runBlocking {
            val expected = 42

            // Act
            val actual = calculator.fetchResultAsync()

            //Assert
            assertEquals(expected,actual,"Expected $expected but got $actual")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `update state flow updates`(){
        testScope.runTest {
            // Arrange
            val newValue = 5
            val expected = 5

            // Act
            val deferredResult = async { calculator.updateStateFlow(newValue) }
            advanceTimeBy(500L)
            deferredResult.await()
            val actual = calculator.stateFlow.value

            // Assert
            assertEquals(expected, actual, "StateFlow value should be updated to 5")
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `update state flow with advanceTimeBy updates state flow value`() = runTest {
        // Arrange
        val newValue = 5
        val expected = 5

        // Act
        advanceTimeBy(500L)
        calculator.updateStateFlow(newValue)
        val actual = calculator.stateFlow.value

        // Assert
        assertEquals(expected, actual, "StateFlow value should be updated to 5")
    }
}