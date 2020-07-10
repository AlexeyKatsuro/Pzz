
package com.alexeykatsuro.pzz.utils

import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.annotation.Px
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import com.alexeykatsuro.pzz.utils.extensions.addDividerItemDecoration
import com.alexeykatsuro.pzz.utils.extensions.setAnimatedVisibility
import com.alexeykatsuro.pzz.utils.extensions.setTranslationVisibility
import com.alexeykatsuro.pzz.utils.extensions.styleAmount
import java.io.File

/**
 * Data Binding adapters specific to the app.
 */

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.isVisible = show
}

@BindingAdapter("visibleInvisible")
fun showHideInvisible(view: View, show: Boolean) {
    view.isInvisible = !show
}

@BindingAdapter("visibleInvisibleFade")
fun setAnimatedShowHideInvisible(view: View, visible: Boolean) {
    view.setAnimatedVisibility(if (visible) View.VISIBLE else View.INVISIBLE)
}

@BindingAdapter("visibleGoneTranslation")
fun setAnimatedShowHideTranslation(view: View, visible: Boolean) {
    view.setTranslationVisibility(if (visible) View.VISIBLE else View.GONE)
}

@BindingAdapter("visibleGoneFade")
fun setAnimatedShowHide(view: View, visible: Boolean) {
    view.setAnimatedVisibility(if (visible) View.VISIBLE else View.GONE)
}

@BindingAdapter("addDividerItemDecoration")
fun addDividerItemDecoration(recyclerView: RecyclerView, required: Boolean) {
    if (required) {
        recyclerView.addDividerItemDecoration()
    }
}

/**
 * Locking focusable behaviour (to block the keyboard) and don't allow text inputting.
 */
@BindingAdapter("nonEditableClickable")
fun setNonEditableClickable(editText: EditText, isNotEnable: Boolean) {
    editText.apply {
        if (isNotEnable) {
            isFocusable = false
            isCursorVisible = false
        } else {
            isFocusable = true
            isCursorVisible = true
        }
    }
}

@BindingAdapter("inflateMenu")
fun setMenu(toolbar: Toolbar, @MenuRes resId: Int?) {
    resId?.let { toolbar.inflateMenu(it) }
}

@BindingAdapter("textHtml")
fun TextView.setHtml(htmlString: String) {
    text = HtmlCompat.fromHtml(htmlString, HtmlCompat.FROM_HTML_MODE_COMPACT)
}

@BindingAdapter("endIconOnClick")
fun setEndIconOnClick(textInputLayout: TextInputLayout, onClickListener: View.OnClickListener) {
    textInputLayout.setEndIconOnClickListener(onClickListener)
}

@BindingAdapter(value = ["textAmount", "textCurrency", "decimalSize"], requireAll = false)
fun setTextAmountStyled(view: TextView, amount: String, currency: String, @Px decimalSize: Float?) {
    val size = if (decimalSize == null || decimalSize == 0.0f) view.textSize else decimalSize
    view.text = amount.styleAmount(view.context, currency, size.toInt())
}

@BindingAdapter("srcFromFile")
fun ImageView.setImageFromFile(imageFile: File?) {
    setImageURI(imageFile?.let { Uri.fromFile(it) })
}

@BindingAdapter("bindTabSelected")
fun bindTabSelected(view: TabLayout, index: Int){
    if (view.selectedTabPosition != index){
        view.getTabAt(index)?.select()
    }
}

@BindingAdapter(value = ["bindQuery","bindQuerySubmit"], requireAll = true)
fun bindQuery(view: SearchView, query: String, submit: Boolean){
    if (!TextUtils.equals(view.query, query)){
        view.setQuery(query,submit)
    }
}
