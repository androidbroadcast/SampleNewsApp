package dev.androidbroadcast.news.database.utils

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

internal class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long): Instant {
        return Instant.fromEpochMilliseconds(value)
    }

    @TypeConverter
    fun dateToTimestamp(instant: Instant): Long {
        return instant.toEpochMilliseconds()
    }
}
