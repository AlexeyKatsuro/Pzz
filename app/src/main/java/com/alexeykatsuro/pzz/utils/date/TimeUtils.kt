/*
 * Copyright 2019 LWO LLC
 */

package com.alexeykatsuro.pzz.utils.date

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun getLocale(): Locale =
    ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]

fun Calendar.toInstantBP(): Instant = Instant.ofEpochMilli(timeInMillis)

object TimeUtils {

    val rawDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val simpleDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    val simpleTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val fullTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val localizedDateFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd MMM yyyy", getLocale()).withZone(ZoneId.systemDefault())
    val docDateFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd.MM.yyyy", getLocale()).withZone(ZoneId.systemDefault())
    val historyDateFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", getLocale())
            .withZone(ZoneId.systemDefault())

    fun localizedDateString(date: String): String {
        val parsedString = rawDateFormatter.parse(date)
        return localizedDateFormatter.format(parsedString)
    }

    fun localizedDateString(instant: Instant): String {
        val parsedInstant = DateTimeFormatter.ISO_INSTANT.parse(instant.toString())
        return localizedDateFormatter.format(parsedInstant)
    }

    fun localizedSimpleDateString(instant: Instant): String {
        val parsedInstant = DateTimeFormatter.ISO_INSTANT.parse(instant.toString())
        return docDateFormatter.format(parsedInstant)
    }

    fun localizedDocDateString(instant: Instant): String {
        val parsedInstant = DateTimeFormatter.ISO_INSTANT.parse(instant.toString())
        return docDateFormatter.format(parsedInstant)
    }

    fun days(days: Int, end: Instant = Instant.now()): Interval {
        val localDateTime = ZonedDateTime.parse(end.toString()).toLocalDateTime()
        val start = localDateTime.minusDays(days.toLong()).toInstant(ZoneOffset.UTC)
        return Interval.of(start, end)
    }

    fun months(months: Int): Interval {
        val end = Instant.now()
        val localDateTime = ZonedDateTime.parse(end.toString()).toLocalDateTime()
        val start = localDateTime.minusMonths(months.toLong()).toInstant(ZoneOffset.UTC)
        return Interval.of(start, end)
    }

    fun years(years: Int): Interval {
        val end = Instant.now()
        val localDateTime = ZonedDateTime.parse(end.toString()).toLocalDateTime()
        val start = localDateTime.minusYears(years.toLong()).toInstant(ZoneOffset.UTC)
        return Interval.of(start, end)
    }


    // From 2017-07-08T11:25:44.97 to 08.07.2017 11:25 with local time zone
    fun unmaskServerDate(dateTime: String, convertToLocal: Boolean): Pair<String, String> {
        return if (dateTime.isNotBlank()) {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneOffset.UTC)
            val zonedDateTime = ZonedDateTime
                .parse(dateTime, formatter)
                .run {
                    if (convertToLocal) withZoneSameInstant(ZoneId.systemDefault()) else this
                }
            val date = zonedDateTime.format(simpleDateFormatter)
            val time = zonedDateTime.format(simpleTimeFormatter)
            date to time
        } else {
            "" to ""
        }
    }

    // From 2017-07-08T11:25:44 to 08.07.2017 11:25:44 with local time zone
    fun unmaskServerHistoryDate(dateTime: String, convertToLocal: Boolean): Pair<String, String> {
        return if (dateTime.isNotBlank()) {
            val formatter = historyDateFormatter
            val zonedDateTime = ZonedDateTime
                .parse(dateTime, formatter)
                .run {
                    if (convertToLocal) withZoneSameInstant(ZoneId.systemDefault()) else this
                }
            val date = zonedDateTime.format(simpleDateFormatter)
            val time = zonedDateTime.format(fullTimeFormatter)
            date to time
        } else {
            "" to ""
        }
    }

    fun getInterval(start: Triple<Int, Int, Int>, end: Triple<Int, Int, Int>): Interval {
        val calendar = Calendar.getInstance()

        calendar.set(start.first, start.second, start.third)
        val dateFrom = calendar.toInstantBP()

        calendar.set(end.first, end.second, end.third)
        val dateTo = calendar.toInstantBP()

        return Interval.of(dateFrom, dateTo)
    }

    /**Note: don't use in loops to avoid object allocation*/
    fun getDateSimple(date: String, format: String): Date? {
        return try {
            SimpleDateFormat(format, Locale.getDefault()).parse(date)
        } catch (ex: ParseException) {
            Timber.e(ex)
            null
        }
    }

    fun getDateSimple(date: String, formatter: SimpleDateFormat): Date? {
        return try {
            formatter.parse(date)
        } catch (ex: ParseException) {
            Timber.e(ex)
            null
        }
    }

    fun getDateTimeInstant(date: String, formatter: DateTimeFormatter): Instant {
        return LocalDateTime.parse(date, formatter).toInstant(ZoneOffset.UTC)
    }

    fun getDateInstant(date: String, formatter: DateTimeFormatter = rawDateFormatter): Instant {
        return LocalDate.parse(date, formatter).atStartOfDay().toInstant(ZoneOffset.UTC)
    }
}

fun Interval.toDateInterval(): Pair<String, String> {
    val zonedStart = ZonedDateTime.ofInstant(start, ZoneOffset.UTC)
    val zonedEnd = ZonedDateTime.ofInstant(end, ZoneOffset.UTC)
    val formatter = DateTimeFormatter.ISO_LOCAL_DATE
    return formatter.format(zonedStart) to formatter.format(zonedEnd)
}
