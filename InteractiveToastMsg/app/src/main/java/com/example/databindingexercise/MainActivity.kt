package com.example.databindingexercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.databindingexercise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // this allows us to reference resource id's by typing binding.nameOfResourceId's
        // instead of using findViewById(R.id.blahblahView)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // referencing the variable created in xml and adding string value here
        binding.name = "Hello world"
        binding.messageButton.text = "Click me"
        // listening to click to change text to "Goodbye!
        binding.messageButton.setOnClickListener {
            Toast.makeText(this, "Button has been clicked!", Toast.LENGTH_SHORT).show()
        }



    }
}