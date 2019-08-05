package dev.carrion.wheelpicker.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseWheelAdapter(val valueList: List<String>, private val callback: (String, Int) -> Unit) :
    RecyclerView.Adapter<BaseWheelViewHolder>() {


    abstract val listMultiplier: Int


    override fun getItemCount(): Int = valueList.size * listMultiplier

    override fun onBindViewHolder(holder: BaseWheelViewHolder, position: Int) {
        holder.bind(valueList[position % valueList.size])
    }
}