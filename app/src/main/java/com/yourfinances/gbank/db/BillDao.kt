package com.yourfinances.gbank.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yourfinances.gbank.data.entities.BillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBill(bill: BillEntity)

    @Query("SELECT * FROM bills WHERE cardId=:cardId")
    fun getBills(cardId: Int): Flow<List<BillEntity>>

}