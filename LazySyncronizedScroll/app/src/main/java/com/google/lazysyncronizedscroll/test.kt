//package com.google.lazysyncronizedscroll
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyListState
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.State
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.key
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.snapshotFlow
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.semantics.contentDescription
//import androidx.compose.ui.semantics.semantics
//import androidx.compose.ui.unit.dp
//import com.google.lazysyncronizedscroll.ui.theme.LazySyncronizedScrollTheme
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.flow.distinctUntilChanged
//import kotlinx.coroutines.launch
//import kotlin.properties.ReadWriteProperty
//import kotlin.reflect.KProperty
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val data = getData()
//        enableEdgeToEdge()
//        setContent {
//            LazySyncronizedScrollTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        modifier = Modifier.padding(innerPadding),data
//                    )
//                }
//            }
//        }
//    }
//}
//
//var currentVal: Int? by AlwaysNullUntil(150, beforeAssignValue = {key->
//    userScrollEnabledMap.map {
//        if (it.key != key){
//            it.value.value = false
//        }
//    }
//
//}, afterAssignValue = {key->
//    userScrollEnabledMap.map {
//        it.value.value = true
//    }
//})
//val map = mutableMapOf<Int,LazyListState>()
//val userScrollEnabledMap = mutableMapOf<Int,MutableState<Boolean>>()
//var currentStatePair:Pair<Int,Int>? = null
//@Composable
//fun Greeting(modifier:Modifier,data:List<RowData>) {
//    val coroutineScope = rememberCoroutineScope()
//    Box {
//        LazyColumn (modifier = modifier
//            .semantics { contentDescription = "MainScrollList" }
//            .padding(top = 20.dp)) {
//            items(data, key = {
//                it.key
//            }){data->
//
//                if(map[data.key] == null){
//                    val state = rememberLazyListState(currentStatePair?.first?:0,currentStatePair?.second?:0)
//                    map[data.key] = state
//                    userScrollEnabledMap[data.key] = remember {
//                        mutableStateOf(true)
//                    }
////                   Log.d("Sathish_SSSS", "Greeting: ${data.key}")
//                    SetUp(data.key,state)
//                }else{
////                   Log.d("Sathish_SSSS", "Greeting: else ${data.key}")
//                    SetUp(data.key,map[data.key]!!)
//                }
//                MyLazyRow(data.key,map[data.key]!!,userScrollEnabledMap[data.key]!!)
//            }
//        }
//    }
//}
//
//@SuppressLint("CoroutineCreationDuringComposition")
//@Composable
//fun SetUp(key: Int, lazyListState:LazyListState) {
//
//    val state = remember {
//        derivedStateOf {
//            lazyListState.firstVisibleItemScrollOffset
//        }
//    }
//
//    CoroutineScope(Dispatchers.Main).launch {
//        state
//    }
//
//
//    LaunchedEffect(lazyListState) {
//        snapshotFlow {
//            lazyListState.firstVisibleItemScrollOffset
//        }.collectLatest { offset ->
//            Log.d("Sathish_SSSS", "Greeting: ${key}  $currentVal")
//            if (currentVal == null || currentVal == key) {
//                val pair = Pair(lazyListState.firstVisibleItemIndex,lazyListState.firstVisibleItemScrollOffset)
//                currentStatePair = pair
//                currentVal = key
//                map.map {
//                    if (it.key != currentVal) {
//                        it.value.scrollToItem(
//                            lazyListState.firstVisibleItemIndex,
//                            offset
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun MyLazyRow(index:Int,state: LazyListState,userScrollEnabled:State<Boolean>){
//    LazyRow(Modifier
//        .fillMaxWidth()
//        .semantics { contentDescription = "lazyRow_$index" }
//        .wrapContentHeight(),state = state, userScrollEnabled = userScrollEnabled.value) {
//        items(1000){
//            Item(it)
//        }
//
//
//    }
//}
//
//@Composable
//fun Item(no:Int){
//    Text(text="text $no", modifier = Modifier
//        .clickable {
//        }
//        .width(150.dp)
//        .height(50.dp)
//        .padding(start = 10.dp, end = 10.dp))
//}
//
//class AlwaysNullUntil<T>(private val time:Long,private val beforeAssignValue:(key:T?)->Unit,private val afterAssignValue:(key:T?)->Unit) : ReadWriteProperty<Any?, T?> {
//
//    private var value: T? = null
//    private var job:Job? = null
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
//            delay(time)
//            value = null
//            afterAssignValue(newValue)
//        }
//    }
//}
//
//data class RowData(val item:String,val key:Int)
//
//fun getData():List<RowData>{
//    val mutableList = mutableListOf<RowData>()
//    for(i in 0..1000){
//        mutableList.add(
//            RowData("text $i",i)
////            RowData(LazyListState(0,0),"text $i",i)
//        )
//    }
//    return mutableList
//}
