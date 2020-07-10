/*
 * Copyright 2018 LWO LLC
 */

@file:JvmName("TextUtils")

package com.alexeykatsuro.pzz.utils.extensions

import android.content.Context
import android.text.Editable
import android.text.SpannedString
import android.text.TextWatcher
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.core.text.*
import com.google.android.material.textfield.TextInputLayout
import com.redmadrobot.inputmask.MaskedTextChangedListener

var TextInputLayout.text: String
    get() = editText!!.text.toString()
    set(value) = editText!!.setText(value)

var EditText.content: String
    get() = text.toString()
    set(value) = setText(value)

var TextInputLayout.textNotEmpty: String
    get() = editText!!.text.toString()
    set(value) {
        if (value.isNotBlank()) editText!!.setText(value)
    }

fun TextInputLayout.setText(@StringRes text: Int) = editText?.setText(text)

fun TextInputLayout.isEmpty() = text.isEmpty()

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable) {
            afterTextChanged(s.toString())
        }
    })
}

fun EditText.setEditable(isEditable: Boolean) {
    isFocusable = isEditable
    isFocusableInTouchMode = isEditable
    isClickable = isEditable
    isLongClickable = isEditable
    isCursorVisible = isEditable
}

fun TextInputLayout.setMask(
    mask: String,
    showHint: Boolean = false,
    handleValue: (extractedValue: String) -> Unit
) {
    editText?.setMask(mask, showHint, handleValue)
}

fun EditText.setMask(
    mask: String,
    showHint: Boolean = false,
    handleValue: (extractedValue: String) -> Unit
) {
    val listener = MaskedTextChangedListener.installOn(
        this,
        mask,
        object : MaskedTextChangedListener.ValueListener {
            override fun onTextChanged(
                maskFilled: Boolean,
                extractedValue: String,
                formattedValue: String
            ) {
                handleValue(extractedValue)
            }
        }
    )
    if (showHint) hint = listener.placeholder()
}

fun TextInputLayout.setOnChildsClickListener(listener: ((View) -> Unit)?) {
    editText?.setOnClickListener(listener)
    setEndIconOnClickListener(listener)
}


fun String.toUnderlinedSpanned(): SpannedString = buildSpannedString {
    underline {
        append(this@toUnderlinedSpanned)
    }
}

@JvmOverloads
fun String?.styleAmount(
    context: Context,
    currency: String? = "",
    decimalSize: Int,
    decimalSeparator: Char = ','
): SpannedString {
    return this?.toDoubleOrNull()?.toDecimalString()?.split('.')?.let {
        buildSpannedString {
            bold {
                append(it[0])

            }
            inSpans(AbsoluteSizeSpan(decimalSize)) {
                color(context.getThemeColor(android.R.attr.textColorTertiary)) {
                    if (it.size == 2) {
                        append(decimalSeparator)
                        append(it[1])
                        if (it[1].length == 1) {
                            append("0")
                        }
                    }
                    if (!currency.isNullOrEmpty()) {
                        append(" $currency")
                    }
                }
            }
        }
    } ?: SpannedString("")
}

