package com.google.lazysyncronizedscroll

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.google.lazysyncronizedscroll.ui.theme.LazySyncronizedScrollTheme
import com.google.lazysyncronizedscroll.ui.theme.RowData
import com.google.lazysyncronizedscroll.ui.theme.getData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = getData()
        enableEdgeToEdge()
        setContent {
            LazySyncronizedScrollTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding), data
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier, data: List<RowData>) {
    val scrollState = rememberLazyListState()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val scrollManager = remember {
        LazyScrollManager<Int>(screenHeight,50.dp,5)
    }
    Box {
        LazyColumn(modifier = modifier
            .semantics { contentDescription = "MainScrollList" }
            .padding(top = 20.dp), state = scrollState) {
            items(data, key = {
                it.key
            }) { data ->
                /*
//                val state = if (map[data.key] == null) {
//                    val state = LazyListState(
//                        currentStatePair.visibleItem ,
//                        currentStatePair.offset
//                    )
//                    map[data.key] = state
//                    userScrollEnabledMap[data.key] = remember {
//                        mutableStateOf(true)
//                    }
//                    setUp(data.key, state)
//                    state
//                } else {
//                    Log.d("Sathish_SSS", "setOffset: ${map[data.key]!!} ")
//
//                    map[data.key]!!
//                }

                 */

                MyLazyRow(scrollManager.getState(data.key))
            }
        }
    }
}

@Composable
fun MyLazyRow(state: LazyListState) {

    /*
    val state = if (map[index] == null) {
        val state = LazyListState(
            currentStatePair.visibleItem ,
            currentStatePair.offset
        )
        map[index] = state
        userScrollEnabledMap[index] = remember {
            mutableStateOf(true)
        }
        setUp(index, state)
        state
    } else {
        Log.d("Sathish_SSS", "setOffset: ${map[index]!!} ")

        map[index]!!
    }

     */

    LazyRow(Modifier
        .fillMaxWidth()
//        .semantics { contentDescription = "lazyRow_$index" }
        .wrapContentHeight(),
        state = state,
//        userScrollEnabled = userScrollEnabledMap[index]?.value ?: true
    ) {
        items(1000) {
            Item(it)
        }
    }
}

@Composable
fun Item(no: Int) {
    Text(
        text = "text $no",
        modifier = Modifier
            .clickable {}
            .width(150.dp)
            .height(50.dp)
            .padding(start = 10.dp, end = 10.dp))
}

//
//
//
//var currentVal: Int? by AlwaysNullUntil(150, beforeAssignValue = { key ->
//    userScrollEnabledMap.map {
//        if (it.key != key) {
//            it.value.value = false
//        }
//    }
//
//}, afterAssignValue = { key ->
//    userScrollEnabledMap.map {
//        it.value.value = true
//    }
//})
//val map = mutableMapOf<Int, LazyListState>()
//val jobMap = mutableMapOf<Int, Job>()
//val userScrollEnabledMap = mutableMapOf<Int, MutableState<Boolean>>()
//val currentStatePair: VisiblePair = VisiblePair()
//
//
//
//fun setUp(key: Int, lazyListState: LazyListState, parentLazyListState: LazyListState? = null) {
//    jobMap[key]?.cancel()
//    jobMap[key] = CoroutineScope(Dispatchers.Main).launch {
//        snapshotFlow {
//            lazyListState.firstVisibleItemScrollOffset
//        }.collect {
//
//            if (currentVal == null || currentVal == key) {
//                currentStatePair.offset = lazyListState.firstVisibleItemScrollOffset
//                currentStatePair.visibleItem = lazyListState.firstVisibleItemIndex
//
//                currentVal = key
//
//                map.map {
//                    if (it.key != currentVal) {
//                        try {
//                            it.value.scrollToItem(
//                                lazyListState.firstVisibleItemIndex,
//                                lazyListState.firstVisibleItemScrollOffset
//                            )
//                        } catch (e: Exception) {
//
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//class AlwaysNullUntil<T>(
//    private val time: Long,
//    private val beforeAssignValue: (key: T?) -> Unit,
//    private val afterAssignValue: (key: T?) -> Unit
//) : ReadWriteProperty<Any?, T?> {
//
//    private var value: T? = null
//    private var job: Job? = null
//
//    override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
//        return value
//    }
//
//    override fun setValue(thisRef: Any?, property: KProperty<*>, newValue: T?) {
//        CoroutineScope(Dispatchers.IO).launch {
//            beforeAssignValue(newValue)
//        }
//        value = newValue
//        job?.cancel()
//        job = CoroutineScope(Dispatchers.IO).launch {
//            try {
//                delay(time)
//                value = null
//                afterAssignValue(newValue)
//            }catch (e:Exception){
//
//            }
//        }
//    }
//}
//
//data class RowData(val item: String, val key: Int)
//
//fun getData(): List<RowData> {
//    val mutableList = mutableListOf<RowData>()
//    for (i in 0..100) {
//        mutableList.add(
//            RowData("text $i", i)
//        )
//    }
//    return mutableList
//}
//
//data class VisiblePair(var offset:Int = 0,var visibleItem: Int = 0)
