package com.example.android.unscramble.ui.game

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

// view model is an abstract class
class GameViewModel1 : ViewModel() {

    // moving data from gamefragment class to this class, the UI controller class
    // separating drawing of views from the data processing
    private val _score = MutableLiveData(0)
    private val _currentWordCount = MutableLiveData(0)

    // changing variable declaration to add a backing property
        // value in the data stored in this variable can be changed
    private val _currentScrambledWord = MutableLiveData<String>()

    // wordList variable will hold list of words to use in game
    private var wordList: MutableList<String> = mutableListOf()
    // currentWord variable will hold the word player is trying to unscramble
    // lateinit keyword will initialize the property later
    private lateinit var currentWord: String

    // this method is accessible and editable only within this class
    // the UI controller(GameFragment) can read only it's values
    val currentScrambledWord: LiveData<Spannable> = Transformations.map(_currentScrambledWord) {
        if (it == null) {
            SpannableString("")
        } else {
            val scrambledWord = it.toString()
            val spannable: Spannable = SpannableString(scrambledWord)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(scrambledWord).build(),
                0,
                scrambledWord.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }

    //
    val score: LiveData<Int>
        get() = _score

    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    // init(initializer block) does initial setup code during initialization of object instance
    // this block of code is run when the object instance is first created and initialized
    init {
        Log.d("GameFragment", "GameViewModel created!")
        // displaying a scrambled word at the start of the app
        getNextWord()
    }


    // Updates currentWord and currentScrambledWord with the next word
    private fun getNextWord() {
        // generating a random word from list
        currentWord = allWordsList.random()
        // converting currentWord to an array of chars and assigning it a new val tempWord
        val tempWord = currentWord.toCharArray()

        // shuffling characters in word until it's not the same as original word
        while (String(tempWord).equals(currentWord, false)) {
            // scrambling words in array to acts as a shuffle
            tempWord.shuffle()
        }

        // checking if a word has been used already.
        // If wordList contains currentWord, call getNextWord().
        if (wordList.contains(currentWord)) {
            getNextWord()
        } else {
            // If not, update value of _currentScrambledWord with new scrambled word.
            // And add new word to wordList
            _currentScrambledWord.value = String(tempWord)
                // inc() function increments a variables value by one with null safety
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordList.add(currentWord)
        }
    }

    /*
    * Returns true if the current word count is less than MAX_NO_OF_WORDS.
    * Updates the next word.
    */
    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }


    private fun increaseScore() {
        // increasing score
            // plus() function increases a variables value with null safety
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    // validating player's word and increasing score if guess is correct.
        // this will update the final score in alert dialog
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }


    // re-initializing  game data to restart the game
    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordList.clear()
        getNextWord()
    }






}