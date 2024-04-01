package com.yourfinances.gbank.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.data.formatCardNumber

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val bankName: String = "",
    val cardNumber: Long = 0L,
    val holderName: String = "",
    val cardIcon: Int,
    val totalBalance: Float
)

fun CardEntity.toModel() = CardModel(
    cardId = id!!,
    bankName = bankName.uppercase(),
    cardNumber = cardNumber.toString().formatCardNumber(),
    holderName = holderName.uppercase(),
    cardIcon = cardIcon,
    totalBalance = totalBalance
)