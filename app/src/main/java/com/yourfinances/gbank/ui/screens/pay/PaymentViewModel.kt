package com.yourfinances.gbank.ui.screens.pay

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourfinances.gbank.data.BillModel
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.data.entities.toModel
import com.yourfinances.gbank.data.fake
import com.yourfinances.gbank.data.isDigitOrDot
import com.yourfinances.gbank.data.isZero
import com.yourfinances.gbank.db.AppDatabase
import com.yourfinances.gbank.db.source.LocalDataSource
import com.yourfinances.gbank.db.source.LocalDataSourceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val cardModel: CardModel? = null,
    val bill: BillModel = BillModel(),
    val bills: List<BillModel> = emptyList()
)

class PaymentViewModel(private val dataSource: LocalDataSource) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    private fun getCardBills(cardModel: CardModel) {
        viewModelScope.launch {
            dataSource.getBills(cardModel.cardId)
                .collectLatest { bills ->
                    if (bills.isNotEmpty()) {
                        _uiState.update {
                            it.copy(
                                bills = bills.map { it.toModel() }
                            )
                        }
                    }
                }
        }
    }

    fun setCard(cardModel: CardModel) {
        _uiState.update {
            it.copy(cardModel = cardModel, bill = it.bill.copy(cardId = cardModel.cardId))
        }
        getCardBills(cardModel)
    }

    fun amount(amount: String) {
        if (amount.isDigitOrDot()) {
            _uiState.update {
                it.copy(bill = it.bill.copy(amount = amount))
            }
        }
    }

    fun receiver(receiver: String) {
        _uiState.update {
            it.copy(bill = it.bill.copy(receiver = receiver))
        }
    }

    fun pay(bill: BillModel) {
        if (bill.amount.isNotBlank()) {
            viewModelScope.launch {
                dataSource.pay(uiState.value.cardModel!!.cardId, bill.amount.toFloat())
                dataSource.insertBill(bill)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class PaymentViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PaymentViewModel(LocalDataSourceProvider.localDataSource(context)) as T
    }
}