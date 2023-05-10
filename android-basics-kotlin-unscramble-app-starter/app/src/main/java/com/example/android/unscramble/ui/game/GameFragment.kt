/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.example.android.unscramble.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewTreeLifecycleOwner
import com.example.android.unscramble.R
import com.example.android.unscramble.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Fragment where the game is played, contains the game logic.
 */
class GameFragment : Fragment() {

    // creating a reference to the viewmodel inside the UI controller
        // associating a viewmodel to a UI controller(activity/fragment)
    private val viewModel1: GameViewModel1 by viewModels()

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding

    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
            // binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
            // above commented code, uses data binding instead of the GameFragmentBinding
            // app now uses data binding and views in layout can access app data
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        // onCreateView() callback is triggered when fragment is created for first time..
        // ..and every time it's re-created for any configuration changes
        Log.d("GameFragment", "GameFragment created/re-created!")
        Log.d("GameFragment", "Word: ${viewModel1.currentScrambledWord} " +
                                        "Score: ${viewModel1.currentWordCount}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the viewModel for data binding - this allows the bound layout access
        // to all the data in the VieWModel
        binding.gameViewModel = viewModel1
        binding.maxNoOfWords = MAX_NO_OF_WORDS

        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }

        // observe the currentScrambledWords LiveData
        // viewLifecycleOwner represents the fragment's view lifecycle
        // viewLifecycleOwner helps the LiveData be aware of the GameFragment lifecycle..
        // ..and notifies the observer only when GameFragment is in active states (STARTED or FINISHED)
            // Observe the scrambledCharArray LiveData, passing in the LifecycleOwner and the observer
            // lambda function with param newWord will contain the new scrambled word value

// removing LiveData observer code for currentScrambledWord:
// don't need observer code in fragment any more since layout receives the updates of the changes to the LiveData directly.
    // now scrambled word text view uses the binding expressions to update the UI, not the LiveData observers!!
//        viewModel1.currentScrambledWord.observe(viewLifecycleOwner,
//            { newWord -> binding.textViewUnscrambledWord.text = newWord })


// Don't need livedata observers anymore since the binding expressions update the UI when corresponding livedata changes!!
        // observers below (score & wordcount) will be triggered when the value of the score and wordcount change inside the viewmodel
        // this will trigger during the lifetime of the lifecycler owner, which is the GameFragment
        // score observer will update the newScore
//        viewModel1.score.observe(viewLifecycleOwner,
//            { newScore -> binding.score.text = getString(R.string.score, newScore)})

        // currentWordCount observer will update the currentWordCount
//        viewModel1.currentWordCount.observe(viewLifecycleOwner,
//            { newWordCount -> binding.wordCount.text = getString(R.string.word_count, newWordCount, MAX_NO_OF_WORDS)})
    }



    // creating and showing an AlertDialog with the final score
    private fun showFinalScoreDialog() {
        // context refers to the context or current state of an application, activity, or fragment
        // it contains info regarding the activity, fragment, or application
        // it's usually used to get access resources, databases, and other system services
            // setTitle(R.string.congratulations) <-- setting the title on alert dialog
            // setMessage(getString(R.string.you_scored, viewModel1.score)) <-- setting message to show final score
            // setCancelable(false) <-- making alert dialog not cancellable when back key is pressed
                // setNegativeButton(getString(R.string.exit)) {
                //     _, _ -> exitGame()
                // }.setPositiveButton(getString(R.string.play_again)) {
                //     _, _ -> restartGame()
                // }
                // adding two buttons(EXIT & PLAY AGAIN) using setNegativeButton(..) & setPositiveButton(..)
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.congratulations)
            .setMessage(getString(R.string.you_scored, viewModel1.score.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) {
                _, _ -> exitGame()
            }.setPositiveButton(getString(R.string.play_again)) {
                _, _ -> restartGame()
            }
            .show()
    }

    /*
    * Checks the user's word, and updates the score accordingly.
    * Displays the next scrambled word.
    */
    private fun onSubmitWord() {
        // storing player's word by extracting it from text field in the binding variable
        val playerWord = binding.textInputEditText.text.toString()

        // validating player's word by checking if word is correct.
        if (viewModel1.isUserWordCorrect((playerWord))) {
            // then, resetting text field
            setErrorTextField(false)
            // checking return value of viewModel.nextWord(),
                // if true, another word is available & update scrambled word on screen using updateNextWordOnScreen()
                // otherwise, game is over and display alert dialog with final score
            if (!viewModel1.nextWord()) {
                showFinalScoreDialog()
            }
        } else {
            // if player's word is incorrect, show an error message in text field
            setErrorTextField(true)
        }
    }

    /*
     * Skips the current word without changing the score.
     */
    private fun onSkipWord() {
        // if there's a next word, display it on the screen & reset text field.
        // Otherwise, there's no more words, then show alert dialog with final score
        if (viewModel1.nextWord()) {
            setErrorTextField(false)
        } else {
            showFinalScoreDialog()
        }
    }


    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        // reinitializing the data to be able to play the game again
        viewModel1.reinitializeData()
        setErrorTextField(false)
    }

    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
    * Sets and resets the text field error status.
    *
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

}
