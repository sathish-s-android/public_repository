package com.expense.track.android.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expense.track.android.R
import com.expense.track.android.Theme.AppColors


@Composable
fun ExpenseSticky(groupExpense: GroupExpense){
    Card(modifier = Modifier.fillMaxWidth()
        .padding(bottom = 20.dp, start = 16.dp, end = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.sticky
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row{
            Text(
                text = groupExpense.groupName,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 16.dp, bottom = 4.dp, top =12.dp)
                    .height(34.dp)
                    .widthIn(min = 20.dp, max = 200.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Image(painter = painterResource(R.drawable.currency_rupee),"",
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 6.dp))
            Text(
                text = "(${groupExpense.totalSpending})",
                modifier = Modifier
                    .padding(start = 0.dp,  bottom = 4.dp, top = 12.dp)
                    .height(34.dp)
                    .align(Alignment.CenterVertically)
                ,
                fontSize = 18.sp,
                color = AppColors.saveButtonBackground,
                fontWeight = FontWeight.Bold
            )

        }
    }
}