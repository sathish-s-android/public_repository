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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.expense.track.android.R
import com.expense.track.android.Theme.AppColors
import com.expense.track.bussinessObjects.Category


@Composable
fun CatagoryList(
    catagoryList: State<List<Category>>,
    selectedItemId:State<Category>,
    itemSelected:(Category)->Unit,
    addItem:(Category)->Unit,
    onDisMiss: () -> Unit
) {
    val showAddDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(color = AppColors.ItemBorder)
            .fillMaxSize()
    ) {
        Box(modifier =  Modifier.absolutePadding(top = 0.dp, left = 20.dp, right = 20.dp)
        .fillMaxSize()
        .border(3.dp, color = AppColors.ItemBorder,shape = RoundedCornerShape(10,10,0,0))
        .background(Color.White, shape = RoundedCornerShape(10,10,0,0)),
        ) {
            SetUpCategory(catagoryList, selectedItemId, itemSelected, onDisMiss)
            FloatingBar(modifier = Modifier.align(Alignment.BottomEnd)) {
                showAddDialog.value = true
            }

            if (showAddDialog.value){
                ShowDialog(addItem) {
                    showAddDialog.value = false
                }
            }

        }
    }
}

@Composable
fun SetUpCategory(catagoryList: State<List<Category>>,selectedItem:State<Category>,itemSelected:(Category)->Unit,onDisMiss: () -> Unit){
    Column {
        Row {
            Image(painter = painterResource(R.drawable.baseline_arrow_back_24),"",
                modifier = Modifier
                    .clickable{
                        onDisMiss()
                    }
                    .padding(start = 20.dp, top = 24.dp, bottom = 10.dp)
                    .clickable {
                    })

            Text(
                text = getString(R.string.select_category),
                Modifier
                    .absolutePadding(left = 10.dp,top=18.dp, bottom = 20.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Spacer(Modifier.weight(1f)
                )
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = catagoryList.value,key={

                it.name
            }){
                Card(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .clickable {
                            itemSelected(it)
                        },
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 5.dp
                    ),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedItem.value.name == it.name) {
                            AppColors.selectedItem
                        }else{
                            AppColors.sticky
                        }
                    ),
                ) {
                    Text(text = it.name,
                        modifier = Modifier
                            .padding(20.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            item {
                Spacer(modifier = Modifier.height(200.dp))
            }
        }
    }
}

@Composable
fun ShowDialog(addItem:(Category)->Unit,onDisMiss:()->Unit){
    val notesEnteredText = remember {
        mutableStateOf("")
    }
    val showError = remember {
        mutableStateOf(false)
    }
    AlertDialog(
        onDismissRequest = {onDisMiss() },
        containerColor = AppColors.dialogBackground,
        title = {
            Text(text = getString(R.string.add_category),
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                EditTextComposable(showError,modifier = Modifier,notesEnteredText, KeyboardType.Text,true){
                    showError.value = false
                    notesEnteredText.value = it
                }
                if (showError.value){
                    Text(text = getString(R.string.empty_error),
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (notesEnteredText.value.isNotEmpty()){
                    addItem(
                        Category(notesEnteredText.value)
                    )
                    onDisMiss()
                }else{
                    showError.value = true
                }
            }) {
                Text(getString(R.string.ok),
                    color = AppColors.saveButtonBackground,
                    fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDisMiss() }) {
                Text(getString(R.string.cancel),
                    color = AppColors.saveButtonBackground,
                    fontWeight = FontWeight.Bold)
            }
        }
    )
}