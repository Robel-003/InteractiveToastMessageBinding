package com.example.affirmations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.affirmations.adapter.ItemAdapter
import com.example.affirmations.data.Datasource
import com.example.affirmations.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // storing the list of affirmations into myDataSet
        val myDataSet = Datasource().loadAffirmations()

        // telling recyclerview to use itemadapter
        binding.recyclerView.adapter = ItemAdapter(this, myDataSet)

        // since layout size in recyclerview is fixed in activity layout,
        // need to set setHasFixedSize param or recyclerview to true
            // this setting is only needed to improve performance.
            // Only use if changes in content do not change layout size of recyclerview
        binding.recyclerView.setHasFixedSize(true)

    }
}