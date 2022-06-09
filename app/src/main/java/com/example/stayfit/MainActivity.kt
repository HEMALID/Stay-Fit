package com.example.stayfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.stayfit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var countDownTimer: CountDownTimer?= null
    private var timerDuration: Long = 60000
    private var pauseOffset:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.flStart.setOnClickListener {
            var i=Intent(this, ExerciseActivity::class.java)
            startActivity(i)
        }

        binding.flBMI.setOnClickListener {
            var i=Intent(this, BMIActivity::class.java)
            startActivity(i)
        }

        binding.flHistory.setOnClickListener {
            var i=Intent(this, HistoryActivity::class.java)
            startActivity(i)
        }
    }
}