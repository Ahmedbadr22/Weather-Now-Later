package com.ab.core.utils

import java.util.Calendar
import java.util.Date

fun isToday(timestamp: Long): Boolean {
    val calendar = Calendar.getInstance()
    val todayYear = calendar.get(Calendar.YEAR)
    val todayMonth = calendar.get(Calendar.MONTH)
    val todayDay = calendar.get(Calendar.DAY_OF_MONTH)

    val date = Calendar.getInstance().apply {
        time = Date(timestamp * 1000) // Convert seconds to milliseconds
    }
    val year = date.get(Calendar.YEAR)
    val month = date.get(Calendar.MONTH)
    val day = date.get(Calendar.DAY_OF_MONTH)

    return todayYear == year && todayMonth == month && todayDay == day
}