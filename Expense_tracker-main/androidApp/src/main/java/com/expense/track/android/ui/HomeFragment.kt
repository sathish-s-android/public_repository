package com.expense.track.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.expense.track.android.R
import com.expense.track.android.Theme.AppColors
import com.expense.track.android.viewModel.ExpenseViewModel
import com.expense.track.android.viewModel.ExpenseViewModelFactory
import com.expense.track.bussinessObjects.Expense
import com.expense.track.bussinessObjects.OrderBy


class HomeFragment: Fragment() {

    private val viewModel by activityViewModels<ExpenseViewModel> {
        ExpenseViewModelFactory()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val composeView = ComposeView(requireContext())

        composeView.setContent {
            SetUpScreen(viewModel.groupExpense.collectAsState(), expenseViewModel = viewModel)
        }

        return composeView
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetUpScreen(expenseListState: State<List<GroupExpense>>,
                expenseViewModel: ExpenseViewModel){

    val showAddBottomSheet = remember {
        mutableStateOf(
            false
        )
    }
    val showUpdateBottomSheet = remember {
        mutableStateOf(
            false
        )
    }

    val showDateRange = remember {
        mutableStateOf(
            false
        )
    }

    val showGroupBy = remember {
        mutableStateOf(
            false
        )
    }

    val totalSpending = remember {
        mutableStateOf(
            false
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Box(modifier = Modifier
                .absolutePadding(top = 40.dp, left = 20.dp, right = 20.dp, bottom = 20.dp),
            ) {
                Row {
                    Text(
                        text = getString(R.string.home),
                        Modifier
                            .align(Alignment.CenterVertically)
                        ,fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                    )
                    Spacer(Modifier.weight(1f))
                    Image(painter = painterResource(R.drawable.currency_rupee),"",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                            .align(Alignment.CenterVertically)
                            .clickable {
                                totalSpending.value = true
                            },
                        contentScale = ContentScale.FillBounds)

                    Image(painter = painterResource(R.drawable.baseline_calendar_month_24),"",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterVertically)
                            .clickable {
                                showDateRange.value = true
                            })
                    Image(painter = painterResource(R.drawable.rounded_ad_group_24),"",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 16.dp)
                            .clickable {
                                showGroupBy.value = true
                            })
                }
            }
            if (expenseListState.value.isEmpty()){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp)) {
                    Image(
                        painter = painterResource(R.drawable.saving_coin),
                        "",
                    )
                    Text(
                        text = getString(R.string.start_tracking_expense),
                        modifier = Modifier
                            .offset(y = -70.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        fontSize = 20.sp
                        , fontWeight = FontWeight.ExtraBold
                    )
                }

            }
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                expenseListState.value.forEach {
                    stickyHeader {
                        ExpenseSticky(it)
                    }
                    items(items = it.itemList, key = { it.id },) {
                        HomeItem(it){expense->
                            expenseViewModel.currentlySelectedItem = expense
                            showUpdateBottomSheet.value = true
                        }
                    }
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .height(200.dp)
                            .width(0.dp)
                    )
                }
            }
        }

        FloatingBar(modifier = Modifier.align(Alignment.BottomEnd)){
            showAddBottomSheet.value = true
        }
        if (showAddBottomSheet.value){
            BottomSheetScaffoldExample(onDismiss = {
                showAddBottomSheet.value = false
            }){
                SetUpAddOrUpdateUi(Type.ADD,expenseViewModel = expenseViewModel,{
                    showAddBottomSheet.value = false
                    expenseViewModel.insertExpense(it)
                }){
                    showAddBottomSheet.value = false
                }
            }
        }
        if (showUpdateBottomSheet.value){
            BottomSheetScaffoldExample(onDismiss = {
                showUpdateBottomSheet.value = false
            }){
                SetUpAddOrUpdateUi(Type.UPDATE,expenseViewModel = expenseViewModel,{
                    expenseViewModel.updateExpense(it)
                }, delete = {
                    it?.also {
                        showUpdateBottomSheet.value = false
                        expenseViewModel.deleteMyExpense(it)
                    }
                }){
                    showUpdateBottomSheet.value = false
                }
            }
        }

        if (showDateRange.value){
            val startDate = expenseViewModel.startDate
            val endDate = expenseViewModel.endDate
            DateRangePickerModal(startDate,endDate,{
                pair->
                expenseViewModel.dateRangeSelected(pair.first,pair.second)
            }, {
                showDateRange.value = false
            }){
                expenseViewModel.resetDateRange()
                showDateRange.value = false
            }
        }

        if (showGroupBy.value){
            BottomSheetScaffoldExample(skipPartiallyExpanded = false, onDismiss = {
                showGroupBy.value = false
            }){
                ShowGroupBy(expenseViewModel.orderBy,expenseViewModel.groupBy,{ groupBy, orderBy->
                    showGroupBy.value = false
                    expenseViewModel.setExpenseGroupAndOrderBy(groupBy,orderBy)
                }){
                    showGroupBy.value = false
                }
            }
        }
        if (totalSpending.value){
            BottomSheetScaffoldExample(skipPartiallyExpanded = false, onDismiss = {
                totalSpending.value = false
            }){
                TotalSpending(expenseViewModel.startDate,expenseViewModel.endDate,expenseViewModel.totalSpending,) {
                    totalSpending.value = false
                }
            }
        }
    }
}

@Composable
fun FloatingBar(modifier:Modifier,clickAction:()->Unit){
    FloatingActionButton(modifier = modifier
        .height(110.dp)
        .width(90.dp)
        .padding(bottom = 50.dp, end = 30.dp)
        ,
        shape = RoundedCornerShape(10.dp),
        containerColor = AppColors.saveButtonBackground,
        contentColor = Color.Black,
        onClick = ({
            clickAction()
        })) {
        Icon(painter = painterResource(R.drawable.baseline_add_24),"add icon",
            modifier = Modifier
                .height(30.dp)
                .width(30.dp))
    }
}


data class GroupExpense(val groupName:String, val itemList:List<Expense>,val totalSpending:Int)





