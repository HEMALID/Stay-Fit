package com.example.stayfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.stayfit.databinding.ActivityFinishBinding
import com.example.stayfit.history.HistoryDao
import com.example.stayfit.history.HistoryEntity
import com.example.stayfit.history.WorkOutApp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private lateinit var binding :ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val historyDao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(historyDao)

        setSupportActionBar(binding.toolbarFinishActivity)

        if (supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbarFinishActivity.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnFinish.setOnClickListener {
            finish()
        }

    }


    private fun addDateToDatabase(historyDao: HistoryDao){

        val c= Calendar.getInstance()
        val dateTime = c.time

        Log.e("TAG","TIME SHOW")
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
        val date = sdf.format(dateTime)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
        }

    }
}