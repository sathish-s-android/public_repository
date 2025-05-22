package com.google.lazysyncronizedscroll


import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

@Stable
class LazyScrollManager<T>(private val totalHeight: Dp, private val individualHeight: Dp, private val threshold:Int = 4):RecycleLazyState<T> {

    private val currentStateKey:AtomicInteger = AtomicInteger(0)
    private val stateStack = mutableListOf<LazyListState>()
    private val keyValuePair = mutableMapOf<T,LazyListState>()
    private val canScrollMap = mutableListOf<MutableState<Boolean>>()
    private val canScrollKeyPair = mutableMapOf<T,MutableState<Boolean>>()
    private val totalCount by lazy {
        ((totalHeight/individualHeight)+threshold).toInt()
    }
    private var currentIndex = 0
    private var currentItemOffset = 0

    private var currentScrollable by EternalNull<Int>(150,{key->
        canScrollMap.forEachIndexed { index, mutableState ->
            if (index != key){
                mutableState.value = false
            }
        }
    }){
        canScrollMap.map {mutableState->
            mutableState.value = true
        }
    }

    override fun getState(key:T):LazyListState {

        if(keyValuePair[key] == null){
            val currentValue = currentStateKey.get()
            val value = getLazyState(currentValue)
            val canScroll = getCanScrollState(currentValue)
            setNextIndex(currentValue)
            keyValuePair[key] = value
            canScrollKeyPair[key] = canScroll
        }
        return keyValuePair[key]!!
    }


    override fun getCanScroll(key: T): State<Boolean> {
        return canScrollKeyPair[key]?: mutableStateOf(true)
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

    private fun getCanScrollState(currentValue:Int):MutableState<Boolean>{
        return if (currentValue <= canScrollMap.size-1){
            canScrollMap[currentValue]
        }else{
            if(currentValue <= totalCount){
                canScrollMap.add(currentValue,mutableStateOf(true))
            }
            canScrollMap[currentValue]
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
    fun getCanScroll(key:T): State<Boolean>
}

class EternalNull<T>(
    private val time: Long,
    private val canScroll:(T?)->Unit,
    private val resetScroll:()->Unit
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
            canScroll(value)
            delay(time)
            value = null
            resetScroll()
        }
    }
}


