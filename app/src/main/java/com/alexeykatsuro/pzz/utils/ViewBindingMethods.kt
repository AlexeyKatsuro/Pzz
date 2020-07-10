package com.alexeykatsuro.pzz.utils

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.google.android.material.card.MaterialCardView

@BindingMethods(
    BindingMethod(
        type = View::class,
        attribute = "outlineProviderInstance",
        method = "setOutlineProvider"
    ),
    BindingMethod(type = View::class, attribute = "clipToOutline", method = "setClipToOutline"),
    BindingMethod(type = View::class, attribute = "activated", method = "setActivated"),
    BindingMethod(type = View::class, attribute = "selected", method = "setSelected"),
    BindingMethod(
        type = Toolbar::class,
        attribute = "onNavigateUpClick",
        method = "setNavigationOnClickListener"
    ),
    BindingMethod(
        type = Toolbar::class,
        attribute = "onMenuClick",
        method = "setOnMenuItemClickListener"
    ),
    BindingMethod(
        type = MaterialCardView::class,
        attribute = "checked",
        method = "setChecked"
    ),

    BindingMethod(type = View::class, attribute = "onLongClick", method = "setOnLongClickListener")
)
class ViewBindingMethods