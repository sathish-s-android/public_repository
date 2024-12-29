package com.expense.track.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expense.track.android.R
import com.expense.track.android.Theme.AppColors
import com.expense.track.android.viewModel.GroupBy
import com.expense.track.bussinessObjects.OrderBy

@Composable
fun ShowGroupBy(orderBy: OrderBy, selectedGroupBy1: GroupBy, onGroupBySelected:(GroupBy, OrderBy)->Unit, onDismiss:()->Unit){
    val currentOrder = remember {
        mutableStateOf(orderBy)
    }

    val currentGroup = remember {
        mutableStateOf(selectedGroupBy1)
    }
    Box(
        modifier = Modifier
            .background(color = AppColors.ItemBorder)
            .fillMaxWidth()
    ) {
        Box(modifier =  Modifier.absolutePadding(top = 0.dp, left = 20.dp, right = 20.dp)
            .fillMaxWidth()
            .border(3.dp, color = AppColors.ItemBorder,shape = RoundedCornerShape(10,10,0,0))
            .background(Color.White, shape = RoundedCornerShape(10,10,0,0)),
        ) {
            Column {
                Row {
                    Image(painter = painterResource(R.drawable.baseline_arrow_back_24),"",
                        modifier = Modifier
                            .clickable{
                                onDismiss()
                            }
                            .padding(start = 20.dp, top = 24.dp, bottom = 10.dp)
                            .clickable {
                            })

                    Text(
                        text = getString(R.string.group_by),
                        Modifier
                            .absolutePadding(left = 10.dp,top=18.dp, bottom = 20.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                    Spacer(
                        Modifier.weight(1f)
                    )

                    Image(painter = painterResource(R.drawable.ok_assert),"",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(end = 20.dp)
                            .clickable{
                                onDismiss()
                            }
                            .clickable {
                                onGroupBySelected(currentGroup.value,currentOrder.value)
                            })
                }
                CustomToogle(Modifier
                    .align(Alignment.CenterHorizontally)
                    .border(3.dp, color = AppColors.ItemBorder, shape = RoundedCornerShape(30)),
                ){
                    Text(
                        text = getString(R.string.asc),
                        Modifier
                            .background(color =
                            if (currentOrder.value == OrderBy.ASC){
                                AppColors.sticky
                            }else{
                                Color.Transparent
                            }
                                ,shape = RoundedCornerShape(30))
                            .clickable {
                                currentOrder.value = OrderBy.ASC
                            }
                            .padding(5.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                    Text(
                        text = getString(R.string.desc),
                        Modifier
                            .background(color =  if (currentOrder.value == OrderBy.DESC){
                                AppColors.sticky
                            }else{
                                Color.Transparent
                            },shape = RoundedCornerShape(30))
                            .clickable {
                                currentOrder.value = OrderBy.DESC
                            }
                            .padding(5.dp)
                            ,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                }
                Card(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .clickable {
                            currentGroup.value = GroupBy.Category
                        },
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 5.dp
                    ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(

                        containerColor = if (currentGroup.value == GroupBy.Category) {
                            AppColors.selectedItem
                        }else{
                            AppColors.sticky
                        }
                    ),
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = getString(R.string.Category),
                            modifier = Modifier
                                .padding(20.dp)
                                .align(alignment = Alignment.Center),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (currentGroup.value == GroupBy.Category){
                            Image(painter = painterResource(R.drawable.baseline_bookmark_border_24), "",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .clickable {

                                    }
                                    .padding(start = 20.dp, end = 20.dp)
                                    .clickable {
                                    })
                        }

                    }
                }

                Card(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .clickable {
                            currentGroup.value = GroupBy.Date
                        }
                    ,
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 5.dp
                    ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(

                        containerColor = if (currentGroup.value == GroupBy.Date) {
                            AppColors.selectedItem
                        }else{
                            AppColors.sticky
                        }
                    ),
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(text = getString(R.string.date),
                            modifier = Modifier
                                .padding(20.dp)
                                .align(alignment = Alignment.Center),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (currentGroup.value == GroupBy.Date){
                            Image(painter = painterResource(R.drawable.baseline_bookmark_border_24),"",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .clickable{

                                    }
                                    .padding(start = 20.dp, end = 20.dp)
                                    .clickable {
                                    })
                        }

                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }

        }
    }

}


@Composable
fun CustomToogle(modifier:Modifier,content:@Composable ()->Unit){
    Layout(modifier = modifier,content=content){measurables,constrains->
        val placeables = measurables.map {
            it.measure(constrains)
        }
        val layoutWidth = ((placeables. maxByOrNull { it. width }?.width ?: 0) * 2)+50.dp.roundToPx()
        val height = 60.dp.roundToPx()
        layout(layoutWidth,height){
            var x = 20.dp.roundToPx()
            val y = ((height/2)-(placeables[0].height/2))

            placeables.forEachIndexed { index, placeable ->
                when(index){
                    0 ->{
                       placeable.placeRelative(x,y)
                        x += placeable.width+20.dp.roundToPx()
                    }
                    1->{
                        placeable.placeRelative(x,y)
                    }
                }
            }
        }
    }

}
