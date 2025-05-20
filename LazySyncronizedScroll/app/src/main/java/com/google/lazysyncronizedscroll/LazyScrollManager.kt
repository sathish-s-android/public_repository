package com.google.lazysyncronizedscroll


import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class LazyScrollManager<T>(private val totalHeight: Dp, private val individualHeight: Dp, private val threshold:Int = 4):RecycleLazyState<T> {

    private var currentScrollable by EternalNull<Int>(150)
    private val currentStateKey:AtomicInteger = AtomicInteger(0)
    private val stateStack = mutableListOf<LazyListState>()
    private val keyValuePair = mutableMapOf<T,LazyListState>()
    private val totalCount by lazy {
        ((totalHeight/individualHeight)+threshold).toInt()
    }
    private var currentIndex = 0
    private var currentItemOffset = 0

    override fun getState(key:T):LazyListState {

        if(keyValuePair[key] == null){
            val currentValue = currentStateKey.get()
            val value = getLazyState(currentValue)
            setNextIndex(currentValue)
            keyValuePair[key] = value
        }

        return keyValuePair[key]!!
    }

    private fun getLazyState(currentValue:Int):LazyListState{
       return if (currentValue <= stateStack.size-1){
             stateStack[currentValue]
        }else{
           if(currentValue <= totalCount){
               val listState = LazyListState(currentIndex,currentItemOffset)
               stateStack.add(currentValue,listState)
               setUp(currentValue,listState)
           }
           stateStack[currentValue]
       }
    }

    private fun setNextIndex(currentValue:Int){
        if (currentValue == totalCount){
            currentStateKey.set(0)
        }
        else{
            currentStateKey.set(currentValue+1)
        }
    }

    private fun setUp(key:Int,lazyListState: LazyListState) {
        CoroutineScope(Dispatchers.Main).launch {
            snapshotFlow {
                lazyListState.firstVisibleItemScrollOffset
            }.collect {

                if (currentScrollable == null || currentScrollable == key) {

                    currentScrollable = key
                    currentIndex = lazyListState.firstVisibleItemIndex
                    currentItemOffset = lazyListState.firstVisibleItemScrollOffset
                    setScroll(key,currentItemOffset)
                }
            }
        }
    }

    private suspend fun setScroll(key:Int,offset:Int){
        stateStack.forEachIndexed { index, currentLazyListState ->
            if (offset != currentItemOffset) return
            if (index != key) {
                currentLazyListState.scrollToItem(currentIndex, currentItemOffset)
            }
        }
    }
}

interface RecycleLazyState<T>{
    fun getState(key:T):LazyListState
}


class EternalNull<T>(
    private val time: Long,
) : ReadWriteProperty<Any?, T?> {

    private var value: T? = null
    private var job: Job? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T?) {
        value = newValue
        job?.cancel()
        job = CoroutineScope(Dispatchers.IO).launch {
            delay(time)
            value = null
        }
    }
}
