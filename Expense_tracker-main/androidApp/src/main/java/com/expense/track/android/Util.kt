package com.expense.track.android

import com.expense.track.bussinessObjects.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Locale

fun Long.toDateString(dateFormat: Int =  DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this)
}

fun getCurrentTime():Long = System.currentTimeMillis()

fun insertDefaultCategory(block:suspend (category: Category)->Unit){
    CoroutineScope(Dispatchers.IO).launch {
        block.invoke(
            Category(name ="Food And Drinks")
        )
        block.invoke(
            Category(name ="Transport")
        )
        block.invoke(
            Category(name ="Shopping")
        )
        block.invoke(
            Category(name ="Entertainment")
        )
        block.invoke(
            Category(name ="Dept payment")
        )
        block.invoke(
            Category(name ="Education")
        )
        block.invoke(
            Category(name ="Health and Fitness")
        )
    }
}

fun String.isTextWithinIntegerRange(): Boolean {
    return this.toIntOrNull() != null
}
