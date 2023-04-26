package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    // the onCreate() method is what opens the app
    // this is just one activity the app performs when it runs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // findViewById() methods finds the button in the layout
        // R.id.Button is the resource IF for the Button which is a unique identifier
            // android automatically assigns ID numbers to the resources in your app
            // the roll button has a resource ID,
            //  and the string for the button text also has a resource ID
        val rollButton: Button = findViewById(R.id.button)

        // setting a listener for the button when it's clicked
        rollButton.setOnClickListener {
            rollDice()
        }

        // doing a dice roll when app starts
        rollDice()
    }

    private fun rollDice() {
/*            // this is pop up message that'll show when the roll button is clicked
            val toast = Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT)
            toast.show()

            // creating a variable and getting the textview by its resource ID
            // then setting a string value to that textview
            val resultTextView: TextView = findViewById(R.id.textView)
            resultTextView.text = "6"
*/
        val rollDice = Dice(6)
        val diceRoll = rollDice.diceRoll()
/*
            val resultTextView: TextView = findViewById(R.id.textView)
            resultTextView.text = diceRoll.toString()
*/
        // creating a variable for the dice image
        val diceImg: ImageView = findViewById(R.id.imageView2)

        // updating the ImageView when button is clicked
        // updating the diceImg to match the diceRoll
        // the when statement returns a resource ID value in the drawableResource variable

        val drawableResource = when (diceRoll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImg.setImageResource(drawableResource)

        // the contentDescription is a text description of what's shown in ImageView..
        // ..so screen readers can describe it
        diceImg.contentDescription = diceRoll.toString()
    }
}

class Dice(private val numOfSide: Int) {

    // return value will be after the function name like below
    fun diceRoll(): Int {
        return (1..numOfSide).random()
    }

}