package com.example.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {

    // lateinit promises the compiler that it'll initialize the variable before using it
    // declaring a top-level variable for the binding object.
    // this will be used across multiple methods in MainActivity class
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this initializes the binding object to access views in the activity.main.xml layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        // instead of passing the resource ID of the layout setContentView(R.layout.activity_main)
        // this specifies the root of the hierarchy of views in the app
        setContentView(binding.root)

        /*
            * now when you need a reference to a view, you get it from the binding object instead of findViewById()
            * the binding object automatically defines references for every view in the app that has an ID
            * you won't need to create a variable to hold the reference for a view, can use a view directly from the binding object

            * old way findViewById():
                * val myButton: Button = findViewById(R.id.my_button)
                * myButton.text = "A Button"

            * better way with view binding
                * val myButton: Button = binding.myButton
                * myButton = "A Button"

            * best way with view binding and no extra variable
                * binding.myButton.text = "A Button"
        */

        // setting a click listener on calculate button and calling calculateTip()
        binding.calculateButton.setOnClickListener { calculateTip() }
    }

    // function to calculate the tip
    fun calculateTip() {
        // since the costOfService is an editable text, we need to convert it to a string
        val serviceCostTextField = binding.costOfService.text.toString()
        val cost = serviceCostTextField.toDouble()
        // getting the checkedRadioButtonId attribute of the tipOptions radioGroup
        val selectedId = binding.tipOptions.checkedRadioButtonId

        // depending on which tip option is selected, the tipPercentage will hold a percentage value
        val tipPercentage = when (selectedId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent-> 0.18
            else -> 0.15
        }
        // using var keyword here b/c user might round up value if switch is select,
            // so the value might change
        var tip = tipPercentage * cost

        // checking if switch element attribute isChecked is on, returns a boolean value
        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }
        // number formatter to format numbers as currency
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

        // getting the formatted tip and display it as a text
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }


}