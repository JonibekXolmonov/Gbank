package com.yourfinances.gbank.ui.screens.singin

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yourfinances.gbank.data.User
import com.yourfinances.gbank.db.source.LocalDataSource
import com.yourfinances.gbank.db.source.LocalDataSourceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UiState(
    val email: String = "",
    val password: String = "",
    val uiType: UiType = UiType.NORMAL
)

enum class UiType {
    NORMAL,
    SING_IN,
    CREATE
}

class AuthViewModel(private val dataSource: LocalDataSource) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState get() = _uiState.asStateFlow()

    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

    fun isValidEmail(email: String): Boolean {
        return email.matches(emailRegex.toRegex())
    }

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun onPasswordChange(password: String) {
        if (password.length<=6) {
            _uiState.update {
                it.copy(password = password)
            }
        }
    }

    fun signIn(onResult: (Boolean) -> Unit) {
        if (isValidEmail(uiState.value.email) &&  uiState.value.password.length == 6) {
            val user = User(email = uiState.value.email, password = uiState.value.password)
            onResult(dataSource.signIn(user))
        }
    }

    fun createAccount(onSuccess: () -> Unit) {
        if (isValidEmail(uiState.value.email) &&  uiState.value.password.length == 6) {
            val user = User(email = uiState.value.email, password = uiState.value.password)
            dataSource.createUser(user)
            onSuccess()
        }
    }

    fun signInType() {
        _uiState.update {
            it.copy(uiType = UiType.SING_IN)
        }
    }

    fun createAccountType() {
        _uiState.update {
            it.copy(uiType = UiType.CREATE)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(LocalDataSourceProvider.localDataSource(context)) as T
    }
}