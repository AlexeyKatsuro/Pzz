package com.alexeykatsuro.pzz.utils.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.alexeykatsuro.pzz.utils.GlobalNavCommand

inline fun <T> Fragment.showOptionsDialog(
    title: String,
    items: List<T>,
    mapper: (T) -> String,
    crossinline onClick: (T) -> Unit
) {

    MaterialDialog(requireContext()).show {
        title(text = title)
        listItems(items = items.map(mapper)) { _, selectedIndex, _ ->
            onClick(items[selectedIndex])
        }
    }
}


inline fun <T> Fragment.showOptionsDialog(
    titleRes: Int,
    items: List<T>,
    mapper: (T) -> String,
    crossinline onClick: (T) -> Unit
) = showOptionsDialog(getString(titleRes), items, mapper, onClick)


fun Fragment.navigateGlobal(navCommand: GlobalNavCommand) {
    val navController = if (navCommand.globalHost == null) {
        findNavController()
    } else {
        Navigation.findNavController(requireActivity(), navCommand.globalHost)
    }
    navController.navigate(navCommand.action, navCommand.args, navCommand.navOptions)
}
