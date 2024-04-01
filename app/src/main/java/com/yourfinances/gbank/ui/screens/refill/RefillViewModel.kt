package com.yourfinances.gbank.ui.screens.refill

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourfinances.gbank.data.BillModel
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.data.entities.toModel
import com.yourfinances.gbank.data.isDigitOrDot
import com.yourfinances.gbank.db.source.LocalDataSource
import com.yourfinances.gbank.db.source.LocalDataSourceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val cardModel: CardModel? = null,
    val amount: String = "",
    val bills: List<BillModel> = emptyList()
)

class RefillViewModel(
    private val dataSource: LocalDataSource
) : ViewModel() {

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

    fun onAmountSet(amount: String) {
        if (amount.isDigitOrDot()) {
            _uiState.update {
                it.copy(amount = amount)
            }
        }
    }

    fun setCard(cardModel: CardModel) {
        _uiState.update {
            it.copy(cardModel = cardModel)
        }
        getCardBills(cardModel)
    }

    fun fill(onSuccess:()->Unit) {
        if (uiState.value.amount.isNotBlank()) {
            viewModelScope.launch {
                dataSource.fill(uiState.value.cardModel!!.cardId, uiState.value.amount.toFloat())
                onSuccess()
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class RefillViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RefillViewModel(LocalDataSourceProvider.localDataSource(context)) as T
    }
}