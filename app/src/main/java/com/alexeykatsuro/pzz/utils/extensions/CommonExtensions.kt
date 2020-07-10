package com.alexeykatsuro.pzz.utils.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import com.airbnb.mvrx.MvRx
import com.alexeykatsuro.pzz.utils.NavCommand
import com.alexeykatsuro.pzz.utils.Timeouts
import okhttp3.OkHttpClient
import retrofit2.HttpException

inline fun <T : ViewDataBinding> T.executeAfter(block: T.() -> Unit) {
    block()
    executePendingBindings()
}

/**
 * Restart activity without saving of navigation graph.
 *
 * Note: Dagger graph won't be recreate.
 */
fun Activity.restart() {
    finish()
    startActivity(Intent(this, this::class.java))
}

/** Restart activity with setting internet options. */
inline fun Activity.restart(setupIntent: Intent.() -> Unit) {
    finish()
    startActivity(Intent(this, this::class.java).apply(setupIntent))
}

/** Restart activity with passing exiting extras into new activity. */
fun Activity.restartWithExtras() {
    restart {
        putExtras(intent)
    }
}

fun <T : Parcelable> NavCommand.applyArgs(args: T): NavCommand =
    copy(args = args.toBundleMvRx())

fun <T : Parcelable> T.toBundleMvRx(): Bundle = bundleOf(MvRx.KEY_ARG to this)


fun OkHttpClient.Builder.setupTimeout(timeouts: Timeouts): OkHttpClient.Builder {
    connectTimeout(timeouts.connect, timeouts.timeUnit)
    readTimeout(timeouts.read, timeouts.timeUnit)
    writeTimeout(timeouts.write, timeouts.timeUnit)
    return this
}

val HttpException.errorResponse: String?
    get() = response()?.errorBody()?.string()

val HttpException.errorMessage: String
    get() {
        val msg = response()!!.errorBody()?.string()
        return if (msg.isNullOrEmpty()) {
            message()
        } else {
            msg
        } ?: "unknown error"
    }

