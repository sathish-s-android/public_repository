package com.expense.track.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expense.track.android.R
import com.expense.track.android.Theme.AppColors
import com.expense.track.bussinessObjects.Expense

@Composable
fun HomeItem(expense: Expense,onClickItem:(Expense)->Unit) {

    Card(Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        .clickable{
            onClickItem(expense)
        }
        .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.ItemBackground
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        )) {
        Row() {
            Column(modifier = Modifier) {
                Row(modifier = Modifier
                    .padding(start = 20.dp, top = 12.dp)
                    .background(color = Color.White, RoundedCornerShape(8.dp)),) {
                    Image(painter = painterResource(R.drawable.currency_rupee),"",
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = 6.dp))
                    Text(
                        text = "  ${expense.spending}  ",
                        Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding( top = 5.dp, bottom = 6.dp)
                            .widthIn(min = 10.dp, max = 200.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 22.sp
                    )
                }
                Text(
                    text = "  ${expense.category}  ",
                    Modifier
                        .padding(start = 10.dp, top = 5.dp, bottom = 10.dp)
                        .widthIn(min = 100.dp, max = 200.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black.copy(alpha = 0.5f),
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                text = "  ${expense.spendingDate}  ",
                Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(end = 20.dp)
                    .widthIn(min = 100.dp, max = 200.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }

}