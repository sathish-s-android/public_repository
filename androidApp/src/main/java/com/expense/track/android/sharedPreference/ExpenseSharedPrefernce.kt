package com.expense.track.android.sharedPreference

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.expense.track.android.MyApplication.Companion.dateStorage
import com.expense.track.android.viewModel.GroupBy
import com.expense.track.bussinessObjects.OrderBy
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

object DataStorage{
    private val fromDatePref = longPreferencesKey("FROM_DATE")
    private val toDatePref = longPreferencesKey("TO_DATE")
    private val groupBy = intPreferencesKey("GROUP_BY")
    private val orderBy = intPreferencesKey("ORDER_BY")

    suspend fun storeStartAndEndDate(fromDate:Long,toDate:Long){
        dateStorage.edit { storage->
            storage[fromDatePref] = fromDate
            storage[toDatePref] = toDate
        }
    }

    suspend fun removeStartAndEndDate(){
        dateStorage.edit { storage->
            storage.remove(fromDatePref)
            storage.remove(toDatePref) }
    }

    suspend fun storeGroupByAndOrderBy(groupByEnum:GroupBy,orderByEnum:OrderBy){
        dateStorage.edit { storage->
            storage[groupBy] = groupByEnum.id
            storage[orderBy] = orderByEnum.id
        }
    }

    suspend fun getGroupAndOrderBy():Pair<GroupBy, OrderBy>{
        val groupBy: Int = dateStorage.data
            .map { preferences ->
                preferences[groupBy]
            }.firstOrNull()?:0

        val orderBy: Int = dateStorage.data
            .map { preferences ->
                preferences[orderBy]
            }.firstOrNull()?:0

        val _groupBy = when(groupBy){
            GroupBy.Category.id->{
                GroupBy.Category
            }
            GroupBy.Date.id->{
                GroupBy.Date
            }

            else -> {
                GroupBy.Category
            }
        }
        val _orderBy = when(orderBy){
            OrderBy.ASC.id->{
                OrderBy.ASC
            }
            OrderBy.DESC.id->{
                OrderBy.DESC
            }

            else -> {
                OrderBy.ASC
            }
        }

        return Pair(_groupBy,_orderBy)
    }

    suspend fun getStartAndEndTime():Pair<Long?,Long?>{
        val fromDate: Long? = dateStorage.data
            .map { preferences ->
                preferences[fromDatePref]
            }.firstOrNull()

        val toDate: Long? = dateStorage.data
            .map { preferences ->
                preferences[toDatePref]
            }.firstOrNull()

        return Pair(fromDate,toDate)
    }
}