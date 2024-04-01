package com.yourfinances.gbank.db.source

import android.content.Context
import com.yourfinances.gbank.data.BillModel
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.data.SharedPref
import com.yourfinances.gbank.data.User
import com.yourfinances.gbank.data.toEntity
import com.yourfinances.gbank.db.AppDatabase
import com.yourfinances.gbank.db.BillDao
import com.yourfinances.gbank.db.CardDao

class LocalDataSource(
    private val cardDao: CardDao,
    private val billDao: BillDao,
    private val sharedPref: SharedPref
) {

    suspend fun insertCard(cardInfo: CardModel) {
        val entity = cardInfo.toEntity()
        cardDao.insertCard(entity)
    }

    suspend fun insertBill(billModel: BillModel) {
        val entity = billModel.toEntity()
        billDao.insertBill(entity)
    }

    fun getBills(cardId: Int) = billDao.getBills(cardId)

    fun getCards() = cardDao.getCards()

    suspend fun totalBalance() = cardDao.totalBalance()

    suspend fun pay(cardId: Int, amount: Float) = cardDao.pay(cardId, amount)
    suspend fun fill(cardId: Int, amount: Float) = cardDao.fill(cardId, amount)

    fun createUser(user: User) {
        sharedPref.email = user.email
        sharedPref.password = user.password
    }

    fun signIn(user: User): Boolean {
        return sharedPref.password == sharedPref.password && sharedPref.email == user.email
    }
}

object LocalDataSourceProvider {
    fun localDataSource(context: Context): LocalDataSource {
        val db = AppDatabase.getInstance(context)
        val cardDao = db.cardDao()
        val billDao = db.billDao()
        val sharedPref = SharedPref(context)
        return LocalDataSource(cardDao, billDao, sharedPref)
    }
}