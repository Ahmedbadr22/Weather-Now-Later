package com.ab.domain.model.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {

    private val formatterPattern = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDate(localDate: LocalDateTime?): String? {
        return localDate?.format(formatterPattern)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDateTime? {
        return dateString?.let { LocalDateTime.parse(it, formatterPattern) }
    }
}
