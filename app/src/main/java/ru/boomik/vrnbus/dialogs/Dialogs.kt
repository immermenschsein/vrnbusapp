package ru.boomik.vrnbus.dialogs

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import android.widget.LinearLayout
import android.widget.EditText
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.TypedValue
import android.widget.TextView
import androidx.core.text.HtmlCompat
import ru.boomik.vrnbus.R


fun alertMultipleChoiceItems(activity: Activity, items: List<String>, selected: (ArrayList<String>?) -> Unit) {
    val selectedItems = ArrayList<String>()

    AlertDialog.Builder(activity)
            .setMultiChoiceItems(items.toTypedArray(), null) { _, which, isChecked ->
                if (isChecked) {
                    // if the user checked the item, add it to the selected items
                    selectedItems.add(items[which])
                } else if (selectedItems.contains(items[which])) {
                    // else if the item is already in the array, remove it
                    selectedItems.remove(items[which])
                }
            }
            .setPositiveButton(activity.getString(R.string.Ок)) { _, _ -> selected(selectedItems) }
            .setNegativeButton(activity.getString(R.string.Cancel)) { _, _ -> selected(null) }
            .setOnCancelListener {
                selected(null)
            }
            .show()

}

fun aboutDialog(activity: Activity) {

    val dpi = activity.resources.displayMetrics.density
    val textView = TextView(activity)
    textView.text = HtmlCompat.fromHtml(activity.getString(R.string.about_html), HtmlCompat.FROM_HTML_MODE_COMPACT)
    textView.movementMethod = LinkMovementMethod.getInstance()
    textView.setPadding(((19 * dpi).toInt()), ((5 * dpi).toInt()), ((14 * dpi).toInt()), ((5 * dpi).toInt()))
    AlertDialog.Builder(activity)
            .setTitle(R.string.about)
            .setView(textView)
            .setPositiveButton(activity.getString(R.string.Ок), null).show()
}

fun alertEnterText(activity: Activity, title: String, selected: (String?) -> Unit) {
    val input = EditText(activity)

    val typedValue = TypedValue()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
        activity.theme.resolveAttribute(android.R.attr.dialogPreferredPadding, typedValue, true)
    }
    val resId = typedValue.resourceId
    val margin = if (resId <= 0) 0 else activity.resources.getDimensionPixelSize(resId) + (-4 * activity.resources.displayMetrics.density).toInt()


    val layout = LinearLayout(activity)
    layout.setPadding(margin, 0, margin, 0)
    layout.addView(input, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))

    AlertDialog.Builder(activity)
            .setTitle(title)
            .setView(layout)
            .setPositiveButton(activity.getString(R.string.Ок)) { _, _ -> selected(input.text.toString()) }
            .setNegativeButton(activity.getString(R.string.Cancel)) { _, _ -> selected(null) }
            .setOnCancelListener {
                selected(null)
            }
            .show()

}
