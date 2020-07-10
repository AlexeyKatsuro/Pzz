/*
 * Copyright 2019 LWO LLC
 */

package com.alexeykatsuro.pzz.data.db

import androidx.room.TypeConverter
import java.io.File
import java.util.*


class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toFile(path: String?): File? {
        return path?.let { File(it) }
    }

    @TypeConverter
    fun fromFile(file: File?): String? {
        return file?.absolutePath
    }

}
