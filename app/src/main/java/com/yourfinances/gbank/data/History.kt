package com.yourfinances.gbank.data

data class History(
    val name: String = "",
    val amount: Float = 0f,
    val isPayment: Boolean = true
)