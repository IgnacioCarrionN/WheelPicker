package dev.carrion.wheelpicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import dev.carrion.wheelpicker.R
import dev.carrion.wheelpicker.base.BaseWheelAdapter
import dev.carrion.wheelpicker.base.BaseWheelViewHolder
import dev.carrion.wheelpicker.viewholder.DefaultViewHolder

class DefaultAdapter(values: List<String>, private val callback: (String, Int) -> Unit) :
    BaseWheelAdapter(values, callback) {

    override val listMultiplier: Int = 4

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWheelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.default_item, parent, false)
        return DefaultViewHolder(view) {
            callback(valueList[it % valueList.size], it)
        }
    }


}