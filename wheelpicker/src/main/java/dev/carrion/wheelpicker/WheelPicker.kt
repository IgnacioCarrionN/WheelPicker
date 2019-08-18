package dev.carrion.wheelpicker

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import dev.carrion.wheelpicker.adapter.DefaultAdapter
import dev.carrion.wheelpicker.base.BaseWheelAdapter
import dev.carrion.wheelpicker.base.BaseWheelViewHolder

class WheelPicker(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr) {


    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    private val wheelRv: RecyclerView
    private val layoutManager: LinearLayoutManager = LinearLayoutManager(context)
    private val snapHelper: SnapHelper = LinearSnapHelper()
    private var snapedItem: Int = RecyclerView.NO_POSITION

    private var snapCenterOnClick: Boolean = true


    private var itemList: List<String>? = null


    init {
        inflate(context, R.layout.wheel_picker, this)
        wheelRv = findViewById(R.id.wheel_picker__rv)
    }


    fun init(
        intRange: IntRange,
        snapCenterOnClick: Boolean = true,
        callback: (String) -> Unit = {}
    ) {
        this.snapCenterOnClick = snapCenterOnClick
        setDefaultAdapter(intRange.map { it.toString() }, callback)
    }

    fun init(
        numberList: List<*>,
        snapCenterOnClick: Boolean = true,
        callback: (String) -> Unit = {}
    ) {
        this.snapCenterOnClick = snapCenterOnClick
        setDefaultAdapter(numberList.map { it.toString() }, callback)
    }

    fun init(
        adapter: BaseWheelAdapter
    ) {
        wheelRv.adapter = adapter
        initRecycler(adapter.valueList)
    }


    private fun initRecycler(valuesList: List<String>) {
        wheelRv.layoutManager = layoutManager
        snapHelper.attachToRecyclerView(wheelRv)
        wheelRv.onFlingListener = LoopFlingListener()
        itemList = valuesList

        val midPosition = valuesList.size * (wheelRv.adapter as BaseWheelAdapter).listMultiplier / 2

        wheelRv.scrollToPosition((midPosition))
        wheelRv.addOnScrollListener(LoopScrollListener())
        centerStart(midPosition)
    }

    private fun setDefaultAdapter(valuesList: List<String>, callback: (String) -> Unit) {
        wheelRv.adapter = DefaultAdapter(valuesList) { value, position ->
            if (snapCenterOnClick) {
                snapToPosition(position)
            }
            callback(value)
        }
        initRecycler(valuesList)
    }


    private fun centerStart(position: Int) {

        wheelRv.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                snapToPosition(position)
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    wheelRv.viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    wheelRv.viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
            }
        })

    }


    fun snapToPosition(position: Int) {
        Log.d("SnapCenter", "Position: $position")
        val viewSelected = wheelRv.layoutManager?.findViewByPosition(position)
        if (viewSelected != null) {
            val snapDistance = snapHelper.calculateDistanceToFinalSnap(layoutManager, viewSelected)
            if (snapDistance != null && (snapDistance[0] != 0 || snapDistance[1] != 0)) {
                wheelRv.scrollBy(snapDistance[0], snapDistance[1])
            }
        }
    }

    fun getSelectedItem(): String {
        val snapView = snapHelper.findSnapView(layoutManager)
        if (snapView != null) {
            val snapPosition = layoutManager.getPosition(snapView)
            itemList?.let {
                return it[snapPosition % it.size]
            } ?: run {
                return "No items in list"
            }
        }
        return "No SnapView"
    }

    private fun updateStateSnapView(oldSnap: Int, newSnap: Int) {
        (wheelRv.findViewHolderForAdapterPosition(oldSnap) as? BaseWheelViewHolder)?.updateView(isSelected = false)
        (wheelRv.findViewHolderForAdapterPosition(newSnap) as? BaseWheelViewHolder)?.updateView(isSelected = true)
    }

    private fun updateVisibleItems() {
        val firstPosition = layoutManager.findFirstVisibleItemPosition()
        val lastPosition = layoutManager.findLastVisibleItemPosition()
        val middle = firstPosition + ((lastPosition - firstPosition) / 2)

        val rotationStep = 75f / (middle - firstPosition)

        for(i in firstPosition..lastPosition) {
            val currentRot = rotationStep * -(i - middle)
            (wheelRv.findViewHolderForAdapterPosition(i) as? BaseWheelViewHolder)?.rotateView(currentRot)
        }
    }


    inner class LoopScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = (wheelRv.layoutManager as LinearLayoutManager)
            itemList?.let { list ->
                val firstItemVisible = layoutManager.findFirstVisibleItemPosition()
                if (firstItemVisible != 1 && firstItemVisible % list.size == 1) {
                    layoutManager.scrollToPosition(1)
                }
                val firstCompletelyItemVisible = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (firstCompletelyItemVisible == 0) {
                    layoutManager.scrollToPositionWithOffset(list.size, 0)
                }

                val currentSnap = snapHelper.getSnapPosition(wheelRv)
                val snapChanged = currentSnap != snapedItem
                if (snapChanged) {
                    updateStateSnapView(snapedItem, currentSnap)
                    snapedItem = currentSnap
                }
                updateVisibleItems()
            }
        }
    }

    inner class LoopFlingListener : RecyclerView.OnFlingListener() {
        override fun onFling(velocityX: Int, velocityY: Int): Boolean {
            return true
        }
    }
}