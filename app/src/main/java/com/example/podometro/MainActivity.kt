package com.example.podometro

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.example.podometro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 1)
        }
        // Sensor pasos
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        Log.d("SensorExamples", sensor.toString())

        //Sensor giroscopio
        val sensor1: Sensor?=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        Log.d("SensorExamples1", sensor1.toString())

        //Sensor pasos
        var pasos: Float = 0.0F
        val sensorEventListener: SensorEventListener = object : SensorEventListener{
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                for(value in sensorEvent.values){
                    pasos += value
                }
                binding.tvPaso.setText("Pasos: $pasos")
                Log.d("SensorExamples", "Value $pasos")
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                //TODO("Not yet implemented")
            }
        }

        //Sensor giroscopio
        val sensorEventListener1: SensorEventListener = object : SensorEventListener{
            override fun onSensorChanged(sensorEvent1: SensorEvent) {
                binding.tvGiro.setText("Giro: ${sensorEvent1.values[0]}, ${sensorEvent1.values[1]}, ${sensorEvent1.values[2]}")
                if(sensorEvent1.values[0]> 0.5f){
                    binding.fondo.setBackgroundColor(Color.GREEN)
                } else if(sensorEvent1.values[0]< -0.5f) {
                    binding.fondo.setBackgroundColor(Color.BLUE)
                }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                //TODO("Not yet implemented")
            }
        }

        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(sensorEventListener1, sensor1, SensorManager.SENSOR_DELAY_NORMAL)
    }
}