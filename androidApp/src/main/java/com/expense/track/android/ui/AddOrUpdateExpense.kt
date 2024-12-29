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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.expense.track.android.MyApplication
import com.expense.track.android.R
import com.expense.track.android.Theme.AppColors
import com.expense.track.android.getCurrentTime
import com.expense.track.android.isTextWithinIntegerRange
import com.expense.track.android.toDateString
import com.expense.track.android.viewModel.ExpenseViewModel
import com.expense.track.bussinessObjects.Category
import com.expense.track.bussinessObjects.Expense


@Composable
fun SetUpAddOrUpdateUi(type:Type,expenseViewModel: ExpenseViewModel,savable:(Expense)->Unit,delete:((Expense?)->Unit)?=null,onDismiss:()->Unit) {

    val showAddDialog = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .background(color = AppColors.ItemBorder)
            .fillMaxSize()
        ) {
        Box(modifier = Modifier
            .absolutePadding(top = 0.dp, left = 20.dp, right = 20.dp)
            .fillMaxSize()
            .border(3.dp, color = AppColors.ItemBorder, shape = RoundedCornerShape(10, 10, 0, 0))
            .background(Color.White, shape = RoundedCornerShape(10, 10, 0, 0)),
            ) {
            Image(painter = painterResource(R.drawable.baseline_arrow_back_24),"",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .padding(start = 20.dp, top = 30.dp)
                    .clickable {
                        onDismiss()
                    })

            Text(
                text = if (type == Type.UPDATE){
                    getString(R.string.update_expense)
                }else{
                    getString(R.string.save_expense)
                },
                Modifier
                    .absolutePadding(left = 60.dp,top=26.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            if (type == Type.UPDATE) {
                Image(painter = painterResource(R.drawable.baseline_delete_24),"",
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd)
                        .padding(start = 20.dp, top = 34.dp, end = 20.dp)
                        .clickable {
                            showAddDialog.value = true
                        })
            }

            SetUpBody(type,expenseViewModel,savable)

        }

        if (showAddDialog.value){
            DeleteDialog({
                showAddDialog.value = false
                delete?.invoke(expenseViewModel.currentlySelectedItem)
            }){
                showAddDialog.value = false
            }
        }

    }
}

@Composable
fun SetUpBody(type:Type,expenseViewModel: ExpenseViewModel,savable:(Expense)->Unit){

    val amountEnteredText = remember {
        mutableStateOf(if (type == Type.UPDATE){
            expenseViewModel.currentlySelectedItem?.spending?.let {
                it.toString()
            }?:""
        }else{
            ""
        } )
    }
    val notesEnteredText = remember {

        mutableStateOf(if (type == Type.UPDATE){
            expenseViewModel.currentlySelectedItem?.notes?:""
        }else{
            ""
        })
    }

    val currentDate = remember {
        mutableStateOf(
            if (type == Type.UPDATE){
                expenseViewModel.currentlySelectedItem?.spendingDate?:getCurrentTime().toDateString()
            }else{
                getCurrentTime().toDateString()
            }

        )
    }

    val currentDateLong = remember {
        mutableLongStateOf(
            if (type == Type.UPDATE){
                expenseViewModel.currentlySelectedItem?.spendTime?:getCurrentTime()
            }else{
                getCurrentTime()
            }
        )
    }

    val category = remember {
        mutableStateOf<Category>(
            if (type == Type.UPDATE){
                expenseViewModel.category.value.find {
                    it.name == expenseViewModel.currentlySelectedItem?.category
                }?:expenseViewModel.category.value[0]
            }else{
                expenseViewModel.category.value[0]
            }

        )
    }

    val showError = remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .padding(top = 70.dp)
        .verticalScroll(rememberScrollState()), ) {
        Text(
            text = getString(R.string.enter_amount),
            Modifier
                .absolutePadding(left = 20.dp, top = 20.dp),
            color = Color.Gray
        )
        Column {
            EditTextComposable(showError,enteredText = amountEnteredText) {
                showError.value = false
                if (it.isTextWithinIntegerRange() || it.isEmpty() ){
                    amountEnteredText.value = it
                }
            }
            if (showError.value){
                Text(text = getString(R.string.empty_error),
                    modifier = Modifier.padding(start = 20.dp),
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }

        Text(
            text = getString(R.string.select_category),
            Modifier
                .absolutePadding(left = 20.dp, top = 10.dp),
            color = Color.Gray
        )

        SetUpCatogoryField(category,expenseViewModel.category.collectAsState(),{
            category.value = it
        }){
            expenseViewModel.insertCategoryTo(it)
        }

        Text(
            text = getString(R.string.selected_date),
            Modifier
                .absolutePadding(left = 20.dp, top = 10.dp),
            color = Color.Gray
        )
        SetUpDateField(currentDate,currentDateLong)
        Text(
            text = getString(R.string.add_notes),
            Modifier
                .absolutePadding(left = 20.dp, top = 10.dp),
            color = Color.Gray
        )
        EditTextComposable(enteredText=notesEnteredText, keyboardTypeL = KeyboardType.Text, singleLine = false){
            notesEnteredText.value = it
        }

        Button(
            modifier = Modifier
                .absolutePadding(left = 20.dp, top = 30.dp, right = 20.dp, bottom = 100.dp)
                .background(color = AppColors.saveButtonBackground, RoundedCornerShape(20.dp))
                .border(
                    width = 2.5.dp,
                    color = AppColors.saveButtonBackground,
                    RoundedCornerShape(20.dp)
                )
                .fillMaxWidth()
                .height(60.dp)
                ,
            onClick = {
                if (amountEnteredText.value.isNotEmpty()){
                    savable(
                        if (type == Type.UPDATE){
                            expenseViewModel.currentlySelectedItem?.id?.let{id->
                                Expense(
                                    id = id,
                                    spendTime = currentDateLong.value,
                                    spendingDate = currentDate.value,
                                    spending = amountEnteredText.value.toInt(),
                                    category = category.value.name,
                                    notes = notesEnteredText.value
                                )
                            }?: Expense(
                                spendTime = currentDateLong.value,
                                spendingDate = currentDate.value,
                                spending = amountEnteredText.value.toInt(),
                                category = category.value.name,
                                notes = notesEnteredText.value
                            )
                        }else{
                            Expense(
                                spendTime = currentDateLong.value,
                                spendingDate = currentDate.value,
                                spending = amountEnteredText.value.toInt(),
                                category = category.value.name,
                                notes = notesEnteredText.value
                            )
                        }

                    )
                }else{
                    showError.value = true
                }

            },
            colors= ButtonDefaults.buttonColors(
                containerColor = AppColors.saveButtonBackground
            )
        ){
            Image(painter = painterResource(R.drawable.baseline_add_24),"",
                modifier = Modifier.align(Alignment.CenterVertically))
            Text(text =  if (type == Type.UPDATE){
                getString(R.string.update_expense)
            }else{
                getString(R.string.save_expense)
            }
           ,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 16.sp)
        }
    }
}

@Composable
fun SetUpCatogoryField(
    category: State<Category>,
    categoryList: State<List<Category>>,
    categorySelected:(Category)->Unit,
    addItem:(Category)->Unit){
    val showDateDialog = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .absolutePadding(left = 20.dp, top = 6.dp, right = 20.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = AppColors.ItemBackground, RoundedCornerShape(20.dp))
            .border(width = 2.5.dp, color = AppColors.ItemBorder, RoundedCornerShape(20.dp))
    ) {
        Text(
            text = category.value.name,
            Modifier
                .absolutePadding(left = 20.dp, top = 6.dp, bottom = 6.dp)
                .height(50.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 18.sp
        )

        Spacer(Modifier.weight(1f))

        Image(painter = painterResource(R.drawable.baseline_arrow_drop_down_24),"",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp, end = 16.dp)
                .clickable {
                    showDateDialog.value = !showDateDialog.value
                })
    }
    if (showDateDialog.value){
        BottomSheetScaffoldExample(paddingTop=20.dp,containerColor = AppColors.ItemBackground, onDismiss = {
            showDateDialog.value = false
        }){
            CatagoryList(categoryList, category,{
                categorySelected(it)
                showDateDialog.value = false
            },{
                addItem(it)
            }){
                showDateDialog.value = false
            }
        }

    }
}

@Composable
fun SetUpDateField(currentDate:MutableState<String>,currentDateLong:MutableState<Long>){

    val showDateDialog = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .absolutePadding(left = 20.dp, top = 6.dp, right = 20.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = AppColors.ItemBackground, RoundedCornerShape(20.dp))
            .border(width = 2.5.dp, color = AppColors.ItemBorder, RoundedCornerShape(20.dp))
    ) {
        Text(
            text = currentDate.value,
            Modifier
                .absolutePadding(left = 20.dp, top = 6.dp, bottom = 6.dp)
                .height(50.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 18.sp
        )
        Spacer(Modifier.weight(1f))

        Image(painter = painterResource(R.drawable.baseline_calendar_month_24),"",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp, end = 16.dp)
                .clickable {
                    showDateDialog.value = !showDateDialog.value
                },
            alignment=Alignment.CenterEnd)
    }
    if (showDateDialog.value){
        DatePickerModal(
            currentDateLong.value,
            {
            it?.also {
                currentDateLong.value = it
                currentDate.value = it.toDateString()
            }

        }) {
            showDateDialog.value = false
        }
    }
}

@Composable
fun EditTextComposable(showError:State<Boolean> = mutableStateOf(false),
                       modifier: Modifier = Modifier
    .absolutePadding(left = 20.dp, top = 6.dp, right = 20.dp), enteredText:State<String>, keyboardTypeL: KeyboardType = KeyboardType.Number, singleLine:Boolean=true, valueBlock :(String)->Unit) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = AppColors.ItemBackground, RoundedCornerShape(20.dp))
            .border(
                width = 2.5.dp, color = if (!showError.value) {
                    AppColors.ItemBorder
                } else {
                    Color.Red
                }, RoundedCornerShape(20.dp)
            ),

    ) {
        TextField(
            modifier = Modifier
                .absolutePadding(left = 6.dp)
                .heightIn(min = 60.dp),
            value = enteredText.value,
            onValueChange = {
                valueBlock(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardTypeL
            ),
            shape = RectangleShape,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.DarkGray,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Gray,
                errorIndicatorColor = Color.Red,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Red
            ),
            maxLines =  if (singleLine) 1 else Int.MAX_VALUE,
            textStyle = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    initialSelectedDateMillis:Long?=null,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis
    )


    DatePickerDialog(
        modifier = Modifier.wrapContentWidth(),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun getString(id: Int): String {
    return ContextCompat.getString(MyApplication.instance!!.applicationContext, id)
}

@Composable
fun Modifier.setBoundry(
    color: Color = Color.Red,
    mutableFocusListener: State<Boolean>? = null
): Modifier {

    val colorBock = mutableFocusListener?.value?.let {
        if (it) {
            color
        } else {
            Color.Gray
        }
    } ?: color
    return drawBehind {
        val width = size.width
        val height = size.height
        drawLine(
            color = colorBock,
            start = Offset(-3f, 0f),
            end = Offset(width + 3, 0f),
            strokeWidth = (2).dp.toPx()
        )
        drawLine(
            color = colorBock,
            start = Offset(0f, 0f),
            end = Offset(0f, height),
            strokeWidth = (2).dp.toPx()
        )
        drawLine(
            color = colorBock,
            start = Offset(-3f, height),
            end = Offset(width + 3, height),
            strokeWidth = (2).dp.toPx()
        )

        drawLine(
            color = colorBock,
            start = Offset(width, 0f),
            end = Offset(width, height),
            strokeWidth = (2).dp.toPx()
        )
    }
}


enum class Type{
    ADD,UPDATE
}


