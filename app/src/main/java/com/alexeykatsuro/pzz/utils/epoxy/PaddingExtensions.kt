package com.alexeykatsuro.pzz.utils.epoxy

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.Dimension
import androidx.annotation.Px
import androidx.core.view.setPadding

@Px
fun Context.dpToPx(@Dimension(unit = Dimension.DP) dp: Int): Int {
    return TypedValue
        .applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
            resources.displayMetrics
        ).toInt()
}

@Px
fun Context.resToPx(@DimenRes itemSpacingRes: Int): Int {
    return if (itemSpacingRes != 0) resources.getDimensionPixelOffset(itemSpacingRes) else 0
}


fun View.setViewPadding(padding: PaddingView?) {

    if (padding == null) {
        setPadding(0)
    } else {
        val paddingPx = padding.toPxType(context)
        setPadding(
            paddingPx.left,
            paddingPx.top,
            paddingPx.right,
            paddingPx.bottom
        )
    }
}

private inline fun PaddingView.mapBy(mapper: (Int) -> (Int)): PaddingView {
    return PaddingView.px(
        if (left != PaddingView.NO_VALUE_SET) mapper(left) else PaddingView.NO_VALUE_SET,
        if (top != PaddingView.NO_VALUE_SET) mapper(top) else PaddingView.NO_VALUE_SET,
        if (right != PaddingView.NO_VALUE_SET) mapper(right) else PaddingView.NO_VALUE_SET,
        if (bottom != PaddingView.NO_VALUE_SET) mapper(bottom) else PaddingView.NO_VALUE_SET
    )
}

fun PaddingView.toPxType(context: Context): PaddingView = when (paddingType) {
    PaddingView.PaddingType.DP -> mapBy { context.dpToPx(it) }
    PaddingView.PaddingType.RESOURCE -> mapBy { context.resToPx(it) }
    PaddingView.PaddingType.PX -> this
}

/** Override padding in a vertical plane with new [padding], without change of paddingType. */
fun PaddingView.vertical(padding: Int): PaddingView =
    copy(top = padding, bottom = padding)


/** Override padding in a horizontal plane with new [padding], without change of paddingType. */
fun PaddingView.horizontal(padding: Int): PaddingView =
    copy(left = padding, right = padding)
