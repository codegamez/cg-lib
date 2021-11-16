@file:Suppress("unused")

package lib.codegames.extension

import android.view.View
import android.view.ViewGroup
import android.widget.*

fun Spinner.onItemSelected(onSelect: (item: Any) -> Unit) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val item = adapter.getItem(position)
            onSelect.invoke(item)
        }
    }
}

fun <T> Spinner.simpleSetup(data: Array<T>, itemTitle: ((T) -> String)? = null) {

    val aa = object : ArrayAdapter<T>(this.context, android.R.layout.simple_spinner_item, data) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val v = super.getView(position, convertView, parent)
            val item = getItem(position)
            v.findViewById<TextView>(android.R.id.text1)?.text = itemTitle?.invoke(item) ?: item.toString()
            return v
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val v = super.getDropDownView(position, convertView, parent)
            val item = getItem(position)
            v.findViewById<TextView>(android.R.id.text1)?.text = itemTitle?.invoke(item) ?: item.toString()
            return v
        }

    }

    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    adapter = aa
}