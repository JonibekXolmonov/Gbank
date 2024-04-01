package com.yourfinances.gbank.data

import androidx.annotation.DrawableRes
import com.yourfinances.gbank.R
import com.yourfinances.gbank.data.entities.CardEntity

data class CardModel(
    val cardId:Int = 0,
    val bankName: String = "",
    val cardNumber: String = "",
    val holderName: String = "",
    @DrawableRes val cardIcon: Int = Int.MAX_VALUE,
    val totalBalance: Float = 0f
)

fun CardModel.fake() =
    CardModel(0,"BANK NAME", "", "NAME SURNAME", R.drawable.ic_card_1)

fun CardModel.toEntity() =
    CardEntity(
        bankName = bankName,
        cardNumber = cardNumber.toLong(),
        holderName = holderName,
        cardIcon = cardIcon,
        totalBalance = totalBalance
    )
