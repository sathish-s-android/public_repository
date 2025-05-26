package com.example.sheetviewtestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
//import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import com.example.sheetviewtestapp.ui.theme.SheetViewTestAppTheme


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val data = getData()
            SheetViewTestAppTheme {
                Scaffold{ padding ->
                    Greeting(
                        modifier = Modifier.padding(padding), data
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier, data: List<ColumnData>) {
    val scrollState = rememberLazyListState()

    val dummyState = remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset
        }
    }
    Box {
        LazyColumn(
            modifier = modifier
                .fillMaxHeight()
                .semantics { contentDescription = "MainScrollList" }
                .padding(top = 20.dp)) {



            items(data, key = {
                it.key
            }) { data ->
                val list= getRowDataList()
                MyLazyRow(state = scrollState, dummyState, list)
            }
        }
    }
}


@Composable
fun MyLazyRow(state: LazyListState, dummyState: State<Int>, list: MutableList<RowData>) {

    Box {
        val myList = remember(list) {
            list.filter { it.key % 2 == 0 }
        }
        Box(
            modifier = Modifier
                .width(dummyState.value.dp)
                .fillMaxHeight()
                .alpha(0F)
        )
        LazyRow(
            Modifier,
            state = state,
        ) {

            itemsIndexed(myList) { index, item ->
                val modifier = Modifier
                    .clickable {}
                Item(item.key, modifier)
            }
        }
    }

}

@Composable
fun Item(no: Int, modifier: Modifier) {

    Row(modifier.wrapContentHeight()) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = ""
        )
        Text(
            text = "text $no",
            modifier = Modifier
                .clickable {}
                .padding(start = 10.dp, end = 10.dp))

    }

}

data class RowData(val item: String, val key: Int)
data class ColumnData( val key: Int, val items:List<RowData>)


fun getData(): List<ColumnData> {
    val parentList = mutableListOf<ColumnData>()
    for (j in 0..1000){

        parentList.add(ColumnData(j,getRowDataList()))
    }

    return parentList
}

fun getRowDataList():MutableList<RowData>{
    val mutableList = mutableListOf<RowData>()
    for (i in 0..1000) {
        mutableList.add(
            RowData("text $i", i)
        )
    }

    return mutableList
}