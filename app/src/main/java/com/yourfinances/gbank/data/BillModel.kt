package com.yourfinances.gbank.data

import com.yourfinances.gbank.data.entities.BillEntity


data class BillModel(
    val cardId: Int = 0,
    val name: String = "",
    val amount: String = "0",
    val receiver: String = ""
)

fun fake() = BillModel(name = "Bill 02", amount = "3500.4")

fun BillModel.toEntity() = BillEntity(
    cardId = cardId,
    name = name,
    amount = amount.toFloat(),
    receiver = receiver.toLong()
)