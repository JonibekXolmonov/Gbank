package com.yourfinances.gbank.ui.screens.card

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourfinances.gbank.R
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.db.source.LocalDataSource
import com.yourfinances.gbank.db.source.LocalDataSourceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AddUiState(
    val error: String? = null,
    val cardModel: CardModel = CardModel(cardIcon = R.drawable.ic_card_1)
)

class AddCardViewModel(
    private val dataSource: LocalDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddUiState())
    val uiState get() = _uiState.asStateFlow()

    fun insertCard(cardModel: CardModel,onSuccess:()->Unit) {
        viewModelScope.launch {
            dataSource.insertCard(cardModel)
            onSuccess()
        }
    }

    fun setBankName(bankName: String) {
        _uiState.update {
            val card = it.cardModel.copy(bankName = bankName)
            it.copy(cardModel = card)
        }
    }

    fun setCardNumber(cardNumber: String) {
        _uiState.update {
            val card = it.cardModel.copy(cardNumber = cardNumber)
            it.copy(cardModel = card)
        }
    }

    fun setHolderName(holderName: String) {
        _uiState.update {
            val card = it.cardModel.copy(holderName = holderName)
            it.copy(cardModel = card)
        }
    }

    fun onIconSelected(cardIcon: Int) {
        _uiState.update {
            val card = it.cardModel.copy(cardIcon = cardIcon)
            it.copy(cardModel = card)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class AddCardViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddCardViewModel(LocalDataSourceProvider.localDataSource(context)) as T
    }
}

