package com.iatli.y2021w5listsensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), SensorEventListener {
    private val list = ArrayList<String>()
    private lateinit var adapter:ArrayAdapter<String>

    private lateinit var sensorManager: SensorManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        loadListViewData()
        
        val listView = findViewById<ListView>(R.id.dynamiclist)
        listView.setOnItemClickListener { _list, _item, _index, _rowid ->
            Toast.makeText(this, "You clicked $_index", Toast.LENGTH_SHORT).show()
            list.removeAt(_index)
            list.add("New Item at $_index")
            adapter.notifyDataSetChanged()
        }


        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also{ accelerometer->
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI)
        }

    }


    private fun loadListViewData(){
        //suppose we download some data from internet

        //put it on arraylist
        for(item in 1..20){
            list.add("Dynamic Content $item")
        }

        //2: create adapter for listview
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        //3: put this adapter into listview
        val listview = findViewById<ListView>(R.id.dynamiclist)
        listview.adapter = adapter
    }

    override fun onSensorChanged(event: SensorEvent?) {
        //TODO("Not yet implemented")
        if (event == null)
            return
        val txt = findViewById<TextView>(R.id.txtsensor)
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            txt.text = "X: ${event.values[0]}, Y:${event.values[1]} Z:${event.values[2]}"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //TODO("Not yet implemented")
    }
}