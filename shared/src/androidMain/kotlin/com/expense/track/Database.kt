package com.expense.track

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.expense.track.data.DataBase.AppDatabase
import com.expense.track.data.DataBase.MyDataBase


fun initializeDatabase(ctx: Context) {
    MyDataBase.setDataBase(getAppDatabase(ctx))
}

fun getAppDatabase(context: Context): AppDatabase {
    val dbFile = context.getDatabasePath("my_room.db")
    return Room.databaseBuilder<AppDatabase>(
        context = context.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}