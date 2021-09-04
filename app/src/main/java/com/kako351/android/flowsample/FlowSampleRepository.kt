package com.kako351.android.flowsample

import kotlinx.coroutines.flow.flow

class FlowSampleRepository {
    private var count = 0

    fun getCount() = count

    fun countUp(): Int {
        count++
        return count
    }

    fun flowCountUp() = flow<Int> {
        emit(countUp())
    }
}
