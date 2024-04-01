package com.yourfinances.gbank.ui.screens.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourfinances.gbank.data.Constant.history
import com.yourfinances.gbank.data.History
import com.yourfinances.gbank.data.fake
import com.yourfinances.gbank.db.source.LocalDataSource
import com.yourfinances.gbank.db.source.LocalDataSourceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val totalAmount: Float = 0f,
    val history: List<History> = emptyList()
)

class HistoryViewModel(private val dataSource: LocalDataSource) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    private fun totalAmount() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    totalAmount = dataSource.totalBalance(),
                    history = history
                )
            }
        }
    }

    init {
        totalAmount()
    }
}

@Suppress("UNCHECKED_CAST")
class HistoryViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryViewModel(LocalDataSourceProvider.localDataSource(context)) as T
    }
}