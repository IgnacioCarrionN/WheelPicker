package dev.carrion.wheelpicker.viewholder

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import dev.carrion.wheelpicker.R
import dev.carrion.wheelpicker.base.BaseWheelViewHolder

class DefaultViewHolder(private val view: View, callback: (Int) -> Unit) : BaseWheelViewHolder(view, callback) {

    private val valueLabel: TextView by lazy { view.findViewById<TextView>(R.id.default_item__label__value) }
    private val valueContainer: LinearLayout by lazy { view.findViewById<LinearLayout>(R.id.default_item__container_value) }

    private var selectedItemColor: Int = ResourcesCompat.getColor(
        view.context.resources,
        R.color.white,
        null
    )
    private var notSelectedItemColor: Int = ResourcesCompat.getColor(
        view.context.resources,
        R.color.light_grey,
        null
    )
    private var selectedTextColor: Int = ResourcesCompat.getColor(
        view.context.resources,
        R.color.grey,
        null
    )
    private val notSelectedTextColor: Int = ResourcesCompat.getColor(
        view.context.resources,
        android.R.color.white,
        null
    )


    override fun bind(value: String) {
        valueLabel.text = value
    }

    override fun updateView(isSelected: Boolean) {
        if (isSelected) {
            valueContainer.setBackgroundColor(selectedItemColor)
            valueLabel.setTextColor(selectedTextColor)
        } else {
            valueContainer.setBackgroundColor(notSelectedItemColor)
            valueLabel.setTextColor(notSelectedTextColor)
        }

    }

    override fun rotateView(rotation: Float) {
        valueLabel.rotationX = rotation
    }
}