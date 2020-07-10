package com.alexeykatsuro.pzz.utils.extensions

import android.app.DatePickerDialog
import android.content.Context
import androidx.fragment.app.Fragment
import org.threeten.bp.Instant
import java.util.*

fun Fragment.showDatePickerInstantDialog(
    options: DatePickerOptions = { },
    currentDate: Instant = Instant.ofEpochMilli(Calendar.getInstance().time.time),
    callback: (instant: Instant) -> Unit
) {
    requireContext().showDatePickerInstantDialog(options, currentDate, callback)
}

fun Context.showDatePickerInstantDialog(
    options: DatePickerOptions = { },
    currentDate: Instant = Instant.ofEpochMilli(Calendar.getInstance().time.time),
    callback: (instant: Instant) -> Unit
) {
    val onDateSelected = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        val calendar = Calendar.getInstance().apply { set(year, month, day) }
        val date = Instant.ofEpochMilli(calendar.time.time)
        callback(date)
    }

    val calendar = Calendar.getInstance()
    calendar.time = currentDate.toDate()
    val (year, month, day) = calendar.getYearMonthDay()

    DatePickerDialog(this, onDateSelected, year, month, day).apply(options).show()
}

fun Instant.toDate(): Date = Date(toEpochMilli())