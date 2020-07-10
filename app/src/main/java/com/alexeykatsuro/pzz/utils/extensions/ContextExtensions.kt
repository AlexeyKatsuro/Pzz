package com.alexeykatsuro.pzz.utils.extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.content.res.use
import androidx.fragment.app.Fragment
import java.io.File

// TODO: This won't work with targetSDK 29 (Android 10). Need to use Firebase Instance ID.
val Context.deviceId: String
    @SuppressLint("HardwareIds")
    get() = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)


fun Activity.hideSoftInput() {
    val imm: InputMethodManager? = getSystemService()
    val currentFocus = currentFocus
    if (currentFocus != null && imm != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()

fun Context.getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any): String {
    return resources.getQuantityString(id, quantity, *formatArgs)
}

fun Context.getQuantityString(id: Int, quantity: Int): String {
    return resources.getQuantityString(id, quantity)
}

@ColorInt
fun Context.getColorCompat(@ColorRes id: Int): Int =
    ContextCompat.getColor(this, id)

fun Context.getColorDrawable(@ColorRes id: Int): ColorDrawable =
    ColorDrawable(getColorCompat(id))

fun Context.getThemeColorDrawable(@AttrRes id: Int): ColorDrawable =
    ColorDrawable(getThemeColor(id))

/**
 * Retrieves the color value by [colorAttr] attribute from the _receiver_ context.
 *
 * This is to avoid direct reference to the color by resource(_R.color.someColor_),
 * because attributes of application color palette(_e.g. colorPrimary, colorSecondary and etc._)
 * may have different color value depend from view's _Context_ or _DayNight Mode_. While
 * R.color.* always constant
 *
 * Use example:
 * ```
 * @ColorInt
 * val color: Int = someView.context.getThemeColor(R.attr.colorPrimary)
 * ```
 */
@SuppressLint("Recycle") // Shortsighted Lint doesn't know about contact of  'use' method
@ColorInt
fun Context.getThemeColor(@AttrRes colorAttr: Int): Int {
    return obtainStyledAttributes(intArrayOf(colorAttr)).use {
        it.getColor(0, Color.MAGENTA)
    }
}

/**
 * Аt the moment hasSystemFeature(FEATURE_FACE) return False for any vendor
 * except Google Pixel 4.
 */
fun Context.hasBiometrics(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
                || if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            packageManager.hasSystemFeature(PackageManager.FEATURE_FACE)
                    || packageManager.hasSystemFeature(PackageManager.FEATURE_IRIS)
        } else false

    } else {
        false
    }
}

fun Context.getTempFile(filename: String) = File(cacheDir, filename)

fun Context.cacheData(filename: String, data: ByteArray) =
    getTempFile(filename).apply {
        writeBytes(data)
    }

fun Context.isNightModeEnabled(): Boolean {
    return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> false
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> false
    }
}

fun Context.getStringOrEmpty(@StringRes res: Int) =
    if (res != 0) getString(res) else ""