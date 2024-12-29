package com.expense.track

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.expense.track.data.DataBase.AppDatabase
import platform.Foundation.NSHomeDirectory

fun getPeopleDatabase(): AppDatabase {
    val dbFile = NSHomeDirectory() + "/people.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile,

//        factory = { AppDatabase::class.instantiateImpl()}
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}