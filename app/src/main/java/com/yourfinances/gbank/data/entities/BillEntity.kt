package com.yourfinances.gbank.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yourfinances.gbank.data.BillModel

@Entity(tableName = "bills")
data class BillEntity(
    @PrimaryKey val id: Int? = null,
    val cardId: Int,
    val name: String,
    val amount: Float,
    val receiver: Long,
)

fun BillEntity.toModel() =
    BillModel(cardId = cardId, name = "Bill ${id.format()}", amount.toString())

private fun Int?.format(): String {
    return if (this in 1..9) "0$this" else this.toString()
}
