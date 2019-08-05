package dev.carrion.wheelsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dev.carrion.wheelpicker.WheelPicker
import dev.carrion.wheelpicker.adapter.DefaultAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wheelPicker.init(1..100, false) {
            Log.d("CLICK", "Item: $it")
        }

        button.setOnClickListener {
            Log.d("Mid snap", "Item: ${wheelPicker.getSelectedItem()}")
        }
    }
}
