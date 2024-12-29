package com.expense.track.android

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.expense.track.initializeDatabase

class MyApplication:Application() {

    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "expense_storage")

        var instance:MyApplication?= null
        val dateStorage:DataStore<Preferences> by lazy {
            instance!!.dataStore
        }
    }



    override fun onCreate() {
        instance = this
        super.onCreate()
        initializeDatabase(this)

    }
}