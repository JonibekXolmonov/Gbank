package com.yourfinances.gbank.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourfinances.gbank.data.entities.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(card: CardEntity)

    @Query("SELECT * FROM cards")
    fun getCards(): Flow<List<CardEntity>>

    @Query("SELECT SUM(totalBalance) FROM cards")
    suspend fun totalBalance(): Float

    @Query("UPDATE cards SET totalBalance = totalBalance - :amount WHERE id = :cardId")
    suspend fun pay(cardId:Int,amount:Float)

    @Query("UPDATE cards SET totalBalance = totalBalance + :amount WHERE id = :cardId")
    suspend fun fill(cardId:Int,amount:Float)
}