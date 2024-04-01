package com.yourfinances.gbank.ui.screens.details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourfinances.gbank.data.BillModel
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.data.entities.toModel
import com.yourfinances.gbank.db.source.LocalDataSource
import com.yourfinances.gbank.db.source.LocalDataSourceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val billInDetails: BillModel? = null,
    val bills: List<BillModel> = emptyList()
)

class DetailsViewModel(private val localDataSource: LocalDataSource) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    fun onBillChosen(bill: BillModel) {
        _uiState.update {
            it.copy(billInDetails = bill)
        }
    }

    fun getBills(cardModel: CardModel) {
        viewModelScope.launch {
            localDataSource.getBills(cardModel.cardId)
                .collectLatest { bills ->
                    if (bills.isNotEmpty()) {
                        _uiState.update {
                            it.copy(
                                billInDetails = bills.first().toModel(),
                                bills = bills.takeLast(bills.size - 1).map { it.toModel() }
                            )
                        }
                    }
                }
        }

    }
}

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(LocalDataSourceProvider.localDataSource(context)) as T
    }
}