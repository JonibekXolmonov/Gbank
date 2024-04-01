package com.yourfinances.gbank.data

import android.content.Context
import com.yourfinances.gbank.data.Constant.EMAIL
import com.yourfinances.gbank.data.Constant.PASSWORD
import com.yourfinances.gbank.data.Constant.SHARED_PREF

class SharedPref(context: Context) {

    private val sharedPref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    var email: String
        set(value) {
            sharedPref.edit().putString(EMAIL, value).apply()
        }
        get() = sharedPref.getString(EMAIL, "") ?: ""

    var password: String
        set(value) {
            sharedPref.edit().putString(PASSWORD, value).apply()
        }
        get() = sharedPref.getString(PASSWORD, "") ?: ""

}