/*
 * Copyright 2019 LWO LLC
 */

package com.alexeykatsuro.pzz.utils.extensions

import android.app.DatePickerDialog
import android.content.Context
import androidx.fragment.app.Fragment
import com.alexeykatsuro.pzz.utils.date.TimeUtils
import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

typealias DatePickerOptions = DatePickerDialog.() -> Unit

const val defaultDatePattern = "dd.MM.yyyy"

fun DatePickerDialog.currentMaxDateOptions() {
    datePicker.maxDate = Calendar.getInstance().timeInMillis
}

fun DatePickerDialog.currentMinDateOptions() {
    datePicker.minDate = Calendar.getInstance().timeInMillis
}

fun Calendar.plus(field: Int, amount: Int): Calendar {
    add(field, amount)
    return this
}

val DatePickerDialog.currentDate: Calendar
    get() = Calendar.getInstance()

inline fun Context.showDatePickerDialog(
    pattern: String = defaultDatePattern,
    options: DatePickerOptions = { },
    currentDate: Date = Calendar.getInstance().time,
    crossinline callback: (date: String) -> Unit
) {
    val onDateSelected = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        val calendar = Calendar.getInstance().apply { set(year, month, day) }
        val date = calendar.time.toFormattedString(pattern)
        callback(date)
    }

    val calendar = Calendar.getInstance()
    calendar.time = currentDate
    val (year, month, day) = calendar.getYearMonthDay()

    DatePickerDialog(this, onDateSelected, year, month, day).apply(options).show()
}

inline fun Fragment.showDatePickerDialog(
    pattern: String = defaultDatePattern,
    options: DatePickerOptions = { },
    currentDate: Date = Calendar.getInstance().time,
    crossinline callback: (date: String) -> Unit
) {
    requireContext().showDatePickerDialog(pattern, options, currentDate, callback)
}

fun String.parseDate(pattern: String = defaultDatePattern): Date {
    return SimpleDateFormat(pattern, Locale.getDefault()).parse(this)!!
}

fun String.parseDateOrNull(pattern: String = defaultDatePattern): Date? {
    return kotlin.runCatching {
        TimeUtils.getDateInstant(
            date = this,
            formatter = DateTimeFormatter.ofPattern(pattern)
        )
    }.getOrNull().toDate()?.takeIf {
        // 1901.01.01 00:00:00 GMT
        it.time >= -2208988800000
    }
}
fun String.parseDateTimeOrNull(pattern: String = defaultDatePattern): Date? {
    return kotlin.runCatching {
        TimeUtils.getDateTimeInstant(
            date = this,
            formatter = DateTimeFormatter.ofPattern(pattern)
        )
    }.getOrNull().toDate()?.takeIf {
        // 1901.01.01 00:00:00 GMT
        it.time >= -2208988800000
    }
}

fun Date.toFormattedString(pattern: String = defaultDatePattern): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

fun Calendar.getYearMonthDay(): Triple<Int, Int, Int> {
    return Triple(
        get(Calendar.YEAR),
        get(Calendar.MONTH),
        get(Calendar.DAY_OF_MONTH)
    )
}

fun String.convertDateFromToPattern(fromPattern: String, toPattern: String): String? {
    return parseDateOrNull(fromPattern)?.toFormattedString(toPattern)
}


fun Instant?.toDate(): Date? = if (this != null) Date(toEpochMilli()) else null