package com.yourfinances.gbank.data

import com.yourfinances.gbank.R

object Constant {
    val cardIcons = listOf(
        R.drawable.ic_card_1,
        R.drawable.ic_card_2,
        R.drawable.ic_card_3,
        R.drawable.ic_card_4,
        R.drawable.ic_card_5,
        R.drawable.ic_card_6,
        R.drawable.ic_card_7,
        R.drawable.ic_card_8,
        R.drawable.ic_card_9,
        R.drawable.ic_card_10,
        R.drawable.ic_card_11,
        R.drawable.ic_card_12,
    )

    val history = listOf(
        History("Sit amet", 570f, true),
        History("Congue", 1200f, false),
        History("Enim", 380f, true),
        History("Fermentum", 211f, false),
        History("Adipiscing", 1200f, true),
        History("Sit amet", 570f, true),
        History("Congue", 1200f, false),
        History("Enim", 380f, true),
        History("Enim", 380f, true),
        History("Fermentum", 211f, false),
        History("Adipiscing", 1200f, true),
    )

    const val SHARED_PREF = "SHARED_PREF"
    const val EMAIL = "email"
    const val PASSWORD = "password"
}