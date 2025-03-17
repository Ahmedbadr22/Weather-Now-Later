package com.ab.core.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date

fun isToday(timestamp: Long): Boolean {
    val date = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
    val today = LocalDate.now(ZoneId.systemDefault())
    return date.isEqual(today)
}

fun isSameDay(timestamp1: Long, timestamp2: Long): Boolean {
    val date1 = Instant.ofEpochMilli(timestamp1).atZone(ZoneOffset.UTC).toLocalDate()
    val date2 = Instant.ofEpochMilli(timestamp2).atZone(ZoneOffset.UTC).toLocalDate()
    return date1.isEqual(date2)
}