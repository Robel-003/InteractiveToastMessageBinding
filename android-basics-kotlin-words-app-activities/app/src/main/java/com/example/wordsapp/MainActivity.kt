/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wordsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.wordsapp.databinding.ActivityMainBinding

/**
 * Main Activity and entry point for the app. Displays a RecyclerView of letters.
 */
class MainActivity : AppCompatActivity() {

    // navController property marked as lateinit since it'll be set in onCreate()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // getting a reference to nav_host_fragment(ID for fragmentContainerView) by assigning it to navController property
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // ensuring action bar(app bar) buttons (menu options in LetterListFragment) are visible
        setupActionBarWithNavController(navController)
    }


    // this method allows the handle of the up button. The activity needs to provide implementation though
    override fun onSupportNavigateUp(): Boolean {
        // Because the navigateUp() function might fail, it returns a Boolean for whether or not it succeeds
        // only need to call super.onSupportNavigateUp() if navigateUp() returns false
        // If navigateUp() returns true, right side never executed.
        // If navigateUp() returns false, then the implementation in the parent class is called
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}
