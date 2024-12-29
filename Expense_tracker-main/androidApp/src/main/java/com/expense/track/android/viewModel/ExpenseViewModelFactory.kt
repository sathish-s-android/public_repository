package com.expense.track.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.expense.track.DependencyProvider
import com.expense.track.usecase.DeleteExpense
import com.expense.track.usecase.GetAllCategory
import com.expense.track.usecase.GetExpense
import com.expense.track.usecase.InsertCategory
import com.expense.track.usecase.InsertExpense
import com.expense.track.usecase.UpdateExpense

class ExpenseViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetExpense::class.java,
            InsertExpense::class.java,
            UpdateExpense::class.java,
            GetAllCategory::class.java,
            InsertCategory::class.java,
            DeleteExpense::class.java
        ).newInstance(
            DependencyProvider.provideGetExpense(),
            DependencyProvider.provideInsertExpense(),
            DependencyProvider.provideUpdateExpense(),
            DependencyProvider.provideGetAllCategory(),
            DependencyProvider.provideInsertCategory(),
            DependencyProvider.provideDeleteExpense()
        )
    }
}



