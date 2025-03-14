package com.ab.core.utils.ext

fun String?.orNA() = this ?: "N/A"
fun String?.ifNull(content: () -> String): String = content()