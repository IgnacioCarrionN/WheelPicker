package dev.carrion.wheelpicker.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseWheelViewHolder(view: View, callback: (Int) -> Unit) : RecyclerView.ViewHolder(view) {

    abstract fun bind(value: String)

    abstract fun updateView(isSelected: Boolean)

    abstract fun rotateView(rotation: Float)

    init {
        view.setOnClickListener {
            callback(adapterPosition)
        }
    }
}