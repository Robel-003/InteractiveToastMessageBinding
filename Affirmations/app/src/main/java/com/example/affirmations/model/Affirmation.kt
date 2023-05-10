package com.example.affirmations.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

// data class must have one property defined
// when creating an instance of affirmation, you need to pass in resource ID for affirmation string
    // the @ annotations prevents user from entering the wrong resource id
data class Affirmation(@StringRes val stringResourceId: Int,
                       @DrawableRes val imageResourceId: Int) {



}