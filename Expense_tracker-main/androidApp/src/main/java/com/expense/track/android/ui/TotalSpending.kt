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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expense.track.android.R
import com.expense.track.android.Theme.AppColors
import com.expense.track.android.toDateString
import com.expense.track.android.viewModel.GroupBy


@Composable
fun TotalSpending(fromDate:Long?=null,toDate:Long?=null,totalSpending:Int,onDismiss:()->Unit){
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
                                onDismiss
                            }
                            .padding(start = 20.dp, top = 24.dp, bottom = 10.dp)
                            .clickable {
                            })

                    Text(
                        text = getString(R.string.total_spending),
                        Modifier
                            .absolutePadding(left = 10.dp,top=18.dp, bottom = 20.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                    Spacer(
                        Modifier.weight(1f)
                    )
                }
                Card(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        ,
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 5.dp
                    ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        AppColors.sticky
                    ),
                ) {
                    Column (modifier = Modifier.fillMaxWidth()) {
                        fromDate?.also {
                            toDate?.also {
                                Row {
                                    Text(
                                        text = getString(R.string.from),
                                        modifier = Modifier
                                            .padding(top = 20.dp, start = 20.dp),
                                        fontSize = 16.sp,
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text ="- ${fromDate.toDateString()}",
                                        modifier = Modifier
                                            .padding(top = 20.dp),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(Modifier.padding(start = 20.dp).height(30.dp).width(2.dp).background(Color.Red))

                                Row {
                                    Text(
                                        text = getString(R.string.to),
                                        modifier = Modifier
                                            .padding(start =20.dp),
                                        fontSize = 16.sp,
                                        color = Color.Gray,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "- ${toDate.toDateString()}",
                                        modifier = Modifier
                                            .padding(bottom = 20.dp),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Row {
                            Text(
                                text = getString(R.string.total),
                                modifier = Modifier
                                    .padding(start =20.dp, top = 28.dp, bottom = 24.dp),
                                fontSize = 16.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(painter = painterResource(R.drawable.currency_rupee),"add icon",
                                modifier = Modifier
                                    .padding(start = 10.dp, top = 24.dp, bottom = 24.dp)
                                    .height(30.dp)
                                    .width(30.dp))
                            Text(
                                text = " $totalSpending",
                                modifier = Modifier
                                    .padding(top = 20.dp, bottom = 24.dp),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }

        }
    }

}