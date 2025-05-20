package com.google.lazysyncronizedscroll.ui.theme


import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


var currentVal: Int? by AlwaysNullUntil(150, beforeAssignValue = { key ->
    userScrollEnabledMap.map {
        if (it.key != key) {
            it.value.value = false
        }
    }

}, afterAssignValue = { key ->
    userScrollEnabledMap.map {
        it.value.value = true
    }
})
val map = mutableMapOf<Int, LazyListState>()
val jobMap = mutableMapOf<Int, Job>()
val userScrollEnabledMap = mutableMapOf<Int, MutableState<Boolean>>()
val currentStatePair: VisiblePair = VisiblePair()


fun setUp(key: Int, lazyListState: LazyListState, parentLazyListState: LazyListState? = null) {
    jobMap[key]?.cancel()
    jobMap[key] = CoroutineScope(Dispatchers.Main).launch {
        snapshotFlow {
            lazyListState.firstVisibleItemScrollOffset
        }.collect {

            if (currentVal == null || currentVal == key) {
                currentStatePair.offset = lazyListState.firstVisibleItemScrollOffset
                currentStatePair.visibleItem = lazyListState.firstVisibleItemIndex
                currentVal = key
                setOffset(lazyListState,currentVal,currentStatePair.offset)
            }
        }
    }
}

private suspend fun setOffset(lazyListState:LazyListState,currentVal:Int?,offset:Int){
    try {
        map.map {
            if (offset != currentStatePair.offset) return

            if (it.key != currentVal) {
//                try {
                    it.value.scrollToItem(
                        lazyListState.firstVisibleItemIndex,
                        lazyListState.firstVisibleItemScrollOffset
                    )
//                } catch (e: Exception) {
//
//                }
            }
        }
    }catch (e:Exception){

    }
}


class AlwaysNullUntil<T>(
    private val time: Long,
    private val beforeAssignValue: (key: T?) -> Unit,
    private val afterAssignValue: (key: T?) -> Unit
) : ReadWriteProperty<Any?, T?> {

    private var value: T? = null
    private var job: Job? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T?) {
        CoroutineScope(Dispatchers.IO).launch {
            beforeAssignValue(newValue)
        }
        value = newValue
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                delay(time)
                value = null
                afterAssignValue(newValue)
            } catch (e: Exception) {

            }
        }
    }
}


data class RowData(val item: String, val key: Int)

fun getData(): List<RowData> {
    val mutableList = mutableListOf<RowData>()
    for (i in 0..1000) {
        mutableList.add(
            RowData("text $i", i)
        )
    }
    return mutableList
}

data class VisiblePair(var offset: Int = 0, var visibleItem: Int = 0)
