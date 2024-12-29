package com.expense.track.android.viewModel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expense.track.android.MyApplication
import com.expense.track.android.R
import com.expense.track.android.insertDefaultCategory
import com.expense.track.android.sharedPreference.DataStorage.getGroupAndOrderBy
import com.expense.track.android.sharedPreference.DataStorage.getStartAndEndTime
import com.expense.track.android.sharedPreference.DataStorage.removeStartAndEndDate
import com.expense.track.android.sharedPreference.DataStorage.storeGroupByAndOrderBy
import com.expense.track.android.sharedPreference.DataStorage.storeStartAndEndDate
import com.expense.track.android.ui.GroupExpense
import com.expense.track.android.ui.getString
import com.expense.track.bussinessObjects.Category
import com.expense.track.bussinessObjects.Expense
import com.expense.track.bussinessObjects.OrderBy
import com.expense.track.usecase.DeleteExpense
import com.expense.track.usecase.GetAllCategory
import com.expense.track.usecase.GetExpense
import com.expense.track.usecase.InsertCategory
import com.expense.track.usecase.InsertExpense
import com.expense.track.usecase.UpdateExpense
import com.expense.track.usecase.getDateElse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val getExpense: GetExpense,
    private val insertExpense: InsertExpense,
    private val updateExpenseUseCase: UpdateExpense,
    private val getAllCategory: GetAllCategory,
    private val insertCategory: InsertCategory,
    private val deleteExpense: DeleteExpense
) : ViewModel() {

    var startDate: Long?= null
    var endDate:Long?= null
    var totalSpending:Int= 0
    var currentlySelectedItem:Expense? = null
    var groupBy:GroupBy = GroupBy.Category
    var orderBy: OrderBy = OrderBy.ASC

    private val _expense: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val expense: StateFlow<List<Expense>> = _expense

    private val _groupExpense: MutableStateFlow<List<GroupExpense>> = MutableStateFlow(emptyList())
    val groupExpense: StateFlow<List<GroupExpense>> = _groupExpense

    private val _category: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    val category: StateFlow<List<Category>> = _category


    private var isCatagoryLoaded = false
    init {
        viewModelScope.launch {
            setUpGroupByAndOrderBy()
            setUpDate()
            getExpenseFromLocal()
            getAllCategoryFromLocal()
        }
    }

    suspend fun setUpGroupByAndOrderBy(){
        getGroupAndOrderBy().also {
            groupBy = it.first
            orderBy = it.second
        }
    }

    private suspend fun setUpDate(){
        getStartAndEndTime().also {
            startDate = it.first
            endDate = it.second
        }
    }

    private suspend fun getExpenseFromLocal() {
        Log.d("Sathish_SSS", "getExpenseFromLocal: ${orderBy}")
        getExpense(GetExpense.Request(startDate?:0,endDate?:0,orderBy)).getDateElse(emptyList()) {
        }.also { expenseList ->
            _expense.value = expenseList
            _groupExpense.value = convertThisUiData(expenseList)

            viewModelScope.launch {
                calculateTotalAmount(expenseList)
            }
        }
    }

    private fun calculateTotalAmount(expenseList:List<Expense>){
        totalSpending = 0
        expenseList.forEach {
            totalSpending += it.spending
        }
    }

    private suspend fun getAllCategoryFromLocal() {
        getAllCategory(GetAllCategory.Request()).getDateElse(emptyList()) {
        }.also {categoryList->
            _category.value = categoryList
            if (categoryList.isEmpty() && isCatagoryLoaded.not()){
                isCatagoryLoaded = true
                insertDefaultCategory(::insertCategoryTo)
                getAllCategoryFromLocal()
            }
        }
    }


    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            updateExpenseUseCase(UpdateExpense.Request(expense))
            Toast.makeText(MyApplication.instance, getString(R.string.update_successful), Toast.LENGTH_SHORT).show()
            getExpenseFromLocal()
        }
    }

     fun insertExpense(expense: Expense) {
         viewModelScope.launch {
             insertExpense(InsertExpense.Request(expense))
             getExpenseFromLocal()
         }

    }

     fun insertCategoryTo(category: Category) {
         viewModelScope.launch {
             insertCategory(InsertCategory.Request(category))
             getAllCategoryFromLocal()
         }
    }

    fun deleteMyExpense(expense: Expense) {
        viewModelScope.launch {
            if (currentlySelectedItem?.id == expense.id){
                currentlySelectedItem = null
            }
            deleteExpense(DeleteExpense.Request(expense))
            getExpenseFromLocal()
        }
    }

    private fun convertThisUiData(groupedList:List<Expense>):List<GroupExpense>{
        val itemsMap= mutableMapOf<String,MutableList<Expense>>()
        val uiListItems= mutableListOf<GroupExpense>()

        when(groupBy){
            GroupBy.Category->{
                groupedList.map {
                    val item = itemsMap[it.category]
                    item?.add(it) ?: itemsMap.put(it.category, mutableListOf(it))
                }
                itemsMap.map {
                    uiListItems.add(GroupExpense(it.key,it.value,it.value.let {
                        var total = 0
                        it.forEach {
                            total += it.spending
                        }
                        total
                    }))
                }
            }
            GroupBy.Date ->{
                groupedList.map {
                    val item = itemsMap[it.spendingDate]
                    item?.add(it) ?: itemsMap.put(it.spendingDate, mutableListOf(it))
                }
                itemsMap.map {
                    uiListItems.add(GroupExpense(it.key,it.value,it.value.let {
                        var total = 0
                        it.forEach {
                            total += it.spending
                        }
                        total
                    }))
                }
            }
        }

        return uiListItems
    }

    fun dateRangeSelected(fromDate:Long?,toDate:Long?){
        viewModelScope.launch {
            fromDate?.also {
                toDate?.also {
                    startDate = fromDate
                    endDate = toDate
                    getExpenseFromLocal()
                    storeStartAndEndDate(fromDate,toDate)
                }
            }
        }
    }
    fun resetDateRange(){
        viewModelScope.launch {
            startDate = null
            endDate = null
            getExpenseFromLocal()
            removeStartAndEndDate()
        }
    }


    fun setExpenseGroupAndOrderBy(groupBy:GroupBy,orderBy: OrderBy){
        this.groupBy = groupBy
        this.orderBy = orderBy
        viewModelScope.launch {
            storeGroupByAndOrderBy(groupBy,orderBy)
        }
        viewModelScope.launch {
            getExpenseFromLocal()
        }
    }
}


enum class GroupBy(val id:Int){
    Category(0),
    Date(1)
}