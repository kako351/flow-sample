package com.kako351.android.flowsample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FlowUnitTest {
    lateinit var repository: FlowSampleRepository

    @Before
    fun setUp() {
        repository = FlowSampleRepository()
    }

    @Test
    fun countTest() {
        runBlockingTest {
            val flow = flow<Int> {
                emit(repository.countUp())
            }
            val count = flow.first()
            Assert.assertTrue(count == 1)
        }
    }

    @Test
    fun debounceTest() {
        runBlockingTest {
            val flow = flow <Int> {
                emit(repository.countUp())
                delay(10)
                emit(repository.countUp())
                emit(repository.countUp())
                delay(1001)
                emit(repository.countUp())
            }.debounce(1000)

            flow.collect {
                println("count = $it")
            }

            Assert.assertTrue(repository.getCount() == 2)
        }
    }


    @Test
    fun debounceTestMultipleFlow() {
        runBlockingTest {
            val flow = repository.flowCountUp().debounce(1000)
            flow.collect { println("count = $it") }
            delay(10)
            flow.collect { println("count = $it") }
            delay(1001)
            flow.collect { println("count = $it") }


            Assert.assertTrue(repository.getCount() == 2)
        }
    }
}
