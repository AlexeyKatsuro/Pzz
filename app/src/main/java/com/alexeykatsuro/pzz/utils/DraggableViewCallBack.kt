package com.alexeykatsuro.pzz.utils

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorInt
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.EpoxyTouchHelper

typealias OnModelMoved<T> = (fromPosition: Int, toPosition: Int, modelBeingMoved: T, itemView: View) -> Unit

/**
 * Callback wrapper for handing view appearance in DRAG_MODE
 *
 * This handling is animation view background to [selectedBackgroundColor]
 * and scale viewSize by 0.5%
 *
 * TODO add supporting not only TRANSPARENT background
 *  Known issue: if you exit DRAG_MODE abruptly, viewSize may not return to its original size
 */
class DraggableViewCallBack<T : EpoxyModel<*>>(private val onModelMovedCallback: OnModelMoved<T>) :
    EpoxyTouchHelper.DragCallbacks<T>() {
    @ColorInt
    val selectedBackgroundColor: Int = Color.argb(200, 200, 200, 200)
    var backgroundAnimator: ValueAnimator? = null

    val viewOriginalColor  =  Color.TRANSPARENT
    override fun onModelMoved(
        fromPosition: Int,
        toPosition: Int,
        modelBeingMoved: T,
        itemView: View
    ) {
        onModelMovedCallback(fromPosition, toPosition, modelBeingMoved, itemView)
    }

    override fun onDragStarted(
        model: T,
        itemView: View,
        adapterPosition: Int
    ) {
        backgroundAnimator = ValueAnimator
            .ofObject(ArgbEvaluator(), viewOriginalColor, selectedBackgroundColor)
        backgroundAnimator?.addUpdateListener { animator ->
            itemView.setBackgroundColor(animator.animatedValue as Int)
        }
        backgroundAnimator?.start()
        // scale viewSize by 0.5%
        itemView.animate().scaleX(1.05f).scaleY(1.05f)
    }

    override fun onDragReleased(model: T, itemView: View) {
        if (backgroundAnimator != null) {
            backgroundAnimator?.cancel()
        }

        backgroundAnimator = ObjectAnimator.ofObject(
            ArgbEvaluator(),
            (itemView.background as ColorDrawable).color,
            viewOriginalColor
        )
        backgroundAnimator?.addUpdateListener { animator: ValueAnimator ->
            itemView.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        backgroundAnimator?.start()

        // return to view original size
        itemView
            .animate()
            .scaleX(1f)
            .scaleY(1f)
    }
}