/*
 * Copyright 2019 LWO LLC
 */

package com.alexeykatsuro.pzz.utils.extensions

import android.content.res.ColorStateList
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Pair for button basic state(_text_ and _OnClickListener_).
 *
 * Useful when the same [Button] should have a different state in different cases.
 */
typealias ButtonConfig = Pair<String, View.OnClickListener>


fun RecyclerView.addDividerItemDecoration() {
    val lm = layoutManager as LinearLayoutManager
    val dividerItemDecoration = DividerItemDecoration(context, lm.orientation)
    addItemDecoration(dividerItemDecoration)
}


fun ImageView.setTint(@ColorRes colorRes: Int) {
    val color = ColorStateList.valueOf(ContextCompat.getColor(context, colorRes))
    ImageViewCompat.setImageTintList(this, color)
}

fun ImageView.setTintColor(@ColorInt color: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
}

fun View.setAnimatedVisibility(visibility: Int) {
    val visible = visibility == View.VISIBLE
    val targetAlpha = if (visible) 1f else 0f
    val anim = animate()
    anim.cancel()
    if (this.visibility == visibility) {
        return
    }
    this.visibility = View.VISIBLE
    anim.alpha(targetAlpha)
    if (!visible) {
        anim.withEndAction {
            this@setAnimatedVisibility.visibility = visibility
        }
    }
}

fun View.setTranslationVisibility(visibility: Int) {
    val visible = visibility == View.VISIBLE
    val targetY = if (visible) this.height else 0
    val anim = animate()
    anim.cancel()

    if (this.visibility == visibility) {
        return
    }
    this.visibility = View.VISIBLE
    anim.translationY(targetY.toFloat())
    if (!visible) {
        anim.withEndAction {
            this@setTranslationVisibility.visibility = visibility
        }
    }
}

inline var View.isGoneFade
    get() = visibility == View.GONE
    set(value) {
        setAnimatedVisibility(if (value) View.GONE else View.VISIBLE)
    }

inline var View.isVisibleFade: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        setAnimatedVisibility(if (value) View.VISIBLE else View.GONE)
    }

inline var View.isInvisibleFade: Boolean
    get() = visibility == View.INVISIBLE
    set(value) {
        setAnimatedVisibility(if (value) View.INVISIBLE else View.VISIBLE)
    }

fun DrawerLayout.setAvailability(isAvailable: Boolean) {
    val mode = if (isAvailable) {
        DrawerLayout.LOCK_MODE_UNLOCKED
    } else {
        DrawerLayout.LOCK_MODE_LOCKED_CLOSED
    }
    setDrawerLockMode(mode)
}
