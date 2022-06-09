package com.example.stayfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stayfit.databinding.ActivityHistotyBinding
import com.example.stayfit.history.HistoryAdapter
import com.example.stayfit.history.HistoryDao
import com.example.stayfit.history.WorkOutApp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHistotyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistotyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistoryActivity)

        if (supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = "History"
        }

        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao = (application as WorkOutApp).db.historyDao()
        getAllCompletedDates(dao)

    }

    private fun getAllCompletedDates(historyDao: HistoryDao){

        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { list ->
                if (list.isNotEmpty()){
                    binding.recycler.visibility = View.VISIBLE
                    binding.noData.visibility = View.GONE

                    binding.recycler.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    var dates = ArrayList<String>()
                    for (date in list){
                        dates.add(date.data)
                    }
                    val historyAdapter = HistoryAdapter(ArrayList(dates))
                    binding.recycler.adapter = historyAdapter

                }else{
                    binding.recycler.visibility = View.GONE
                    binding.noData.visibility = View.VISIBLE
                }
            }
        }

    }

}