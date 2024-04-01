package bek.yourfinances.gbank.ui.screens.card

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.data.entities.toModel
import com.yourfinances.gbank.data.sumOf
import com.yourfinances.gbank.db.AppDatabase
import com.yourfinances.gbank.db.source.LocalDataSource
import com.yourfinances.gbank.db.source.LocalDataSourceProvider.localDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val cards: List<CardModel> = emptyList(),
    val totalBalance: Float = 0f
)

class CardsViewModel(
    private val dataSource: LocalDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    private fun getCards() {
        viewModelScope.launch {
            dataSource.getCards()
                .collectLatest {
                    val cards = it.map { it.toModel() }
                    if (cards.isNotEmpty()) {
                        _uiState.update {
                            it.copy(cards = cards, totalBalance = cards.first().totalBalance)
                        }
                    }
                }
        }
    }

    fun onCardSwipe(cardModel: CardModel) {
        _uiState.update {
            it.copy(
                totalBalance = cardModel.totalBalance
            )
        }
    }

    init {
        getCards()
    }
}

@Suppress("UNCHECKED_CAST")
class CardsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CardsViewModel(localDataSource(context)) as T
    }
}