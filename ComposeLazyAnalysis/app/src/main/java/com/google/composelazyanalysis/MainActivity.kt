package com.google.composelazyanalysis


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.composelazyanalysis.ui.theme.ComposeLazyAnalysisTheme
import com.google.composelazyanalysis.ui.theme.c1
import com.google.composelazyanalysis.ui.theme.c2
import com.google.composelazyanalysis.ui.theme.c3
import com.google.composelazyanalysis.ui.theme.c4

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeLazyAnalysisTheme {
                val data = remember {
                    derivedStateOf {
                        DataUtil.data
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        data
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier,state: State<List<RVData>>) {

    Color.Red
    LazyColumn(modifier) {
        items(items = state.value, key = {
            it.id
        }){
            when(it.viewType){
                0->{
                    Item(it)
                }
                1->{
                    Item1(it)
                }
                2->{
                    Item2(it)
                }
                3->{
                    Item3(it)
                }
                else->{
                    Item(it)
                }
            }
        }
    }
}

@Composable
fun Item(rVData:RVData){
    CoreItem(rVData, c1)
}

@Composable
fun Item1(rVData:RVData){
    CoreItem(rVData, c2)
}

@Composable
fun Item2(rVData:RVData){
    CoreItem(rVData, c3)
}

@Composable
fun Item3(rVData:RVData){
    CoreItem(rVData, c4)
}

@Composable
fun CoreItem(rVData:RVData,color:Color){
    Row(Modifier.background(color).wrapContentHeight().padding(5.dp)){
        Image(
            painterResource(rVData.goku),
            modifier = Modifier
                .height(100.dp)
                .width(100.dp),
            contentDescription = ""
        )
        Column {
            Row(modifier = Modifier.wrapContentHeight()) {
                Text(
                    text = rVData.name,
                    modifier = Modifier
                        .width(150.dp)
                        .wrapContentHeight().align(alignment = Alignment.CenterVertically),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Column(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painterResource(rVData.drawable1),
                            modifier = Modifier.weight(1f),
                            contentDescription = ""
                        )
                        Image(
                            painterResource(rVData.drawable2),
                            modifier = Modifier.weight(1f),
                            contentDescription = ""
                        )
                    }

                    Text(
                        text = rVData.neutralContent,
                        modifier = Modifier
                            .width(150.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }

            }

            Row(modifier = Modifier.wrapContentHeight()) {
                Button(onClick = {},modifier = Modifier
                    .height(50.dp)
                    .width(140.dp)){
                    Text(
                        text = rVData.buttonContent,
                        modifier = Modifier
                            .width(150.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = rVData.dummyContent,
                    modifier = Modifier
                        .width(150.dp)
                        .weight(1F)
                        .wrapContentHeight().align(alignment = Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}