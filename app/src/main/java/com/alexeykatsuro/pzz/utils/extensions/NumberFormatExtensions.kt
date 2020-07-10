package com.alexeykatsuro.pzz.utils.extensions

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import kotlin.math.roundToInt

fun Double.toStringWithSign(): String =
    if (this > 0) "+$this" else this.toString()  // negative double parses correctly

fun String.toDoubleOrZero(): Double = if (this.isNotEmpty()) this.toDouble() else 0.0

fun Int.progressOf(max: Int) = (this / max.toDouble() * 100).roundToInt()

fun Double.toDecimalString(pattern: String = "#,##0.00"): String {
    val dfs = DecimalFormatSymbols().apply {
        decimalSeparator = '.'
        groupingSeparator = ' '
    }
    val df = DecimalFormat(pattern, dfs).apply {
        roundingMode = RoundingMode.CEILING
    }
    return df.format(this)
}

/** Translate card number from "1111 2222 3333 4444 " to "111122******4444". */
fun String.createSecretCardView(secretChar: Char = '*'): String = buildString {
    val str = this@createSecretCardView.trim().replace(" ","")

    str.forEachIndexed { index, c ->
        append(if (index in 6 until str.length-4) secretChar else c)
    }
}

fun String.truncateAmountToDouble() = this.toDouble() / 100

fun Double.fillAmountToString() =
    "%.2f".format(this).replace(",", "")


/*fun String.styleExchangeRatesDelta(context: Context): SpannedString {
    return buildSpannedString {
        color(
            when {
                startsWith('-') -> ContextCompat.getColor(
                    context,
                    R.color.red
                )
                startsWith('+') -> ContextCompat.getColor(
                    context,
                    R.color.green
                )
                else -> ContextCompat.getColor(
                    context,
                    R.color.light_gray_text
                )
            }
        ) {
            append(this@styleExchangeRatesDelta)
        }
    }
}*/

/*
fun String.styleAutopaymentStatus(context: Context): SpannedString {
    return buildSpannedString {
        color(
            when (this@styleAutopaymentStatus) {
                "0" -> ContextCompat.getColor(context, R.color.green)
                "1", "3" -> ContextCompat.getColor(
                    context,
                    R.color.turquoise
                )
                "2" -> ContextCompat.getColor(context, R.color.red)
                else -> ContextCompat.getColor(
                    context,
                    R.color.light_gray_text
                )
            }
        ) {
            append(
                when (this@styleAutopaymentStatus) {
                    "0" -> context.getString(R.string.autopayment_executed)
                    "1" -> context.getString(R.string.autopayment_processing)
                    "2" -> context.getString(R.string.autopayment_error)
                    "3" -> context.getString(R.string.autopayment_waiting)
                    else -> ""
                }
            )
        }
    }
}*/
