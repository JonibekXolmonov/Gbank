package com.yourfinances.gbank.ui.screens.singin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.R
import com.yourfinances.gbank.data.advancedShadow
import com.yourfinances.gbank.ui.screens.card.ActionButton
import com.yourfinances.gbank.ui.screens.destinations.CardsScreenDestination
import com.yourfinances.gbank.ui.theme.Background
import com.yourfinances.gbank.ui.theme.CardContainer
import com.yourfinances.gbank.ui.theme.Teal

@Destination
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = AuthViewModelFactory(
            LocalContext.current
        )
    ),
    navigator: DestinationsNavigator
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(start = 12.dp, end = 24.dp, top = 12.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 48.dp)
        ) {
            Text(
                text = stringResource(R.string.banking_app),
                style = MaterialTheme.typography.titleMedium
            )
            Image(
                painter = painterResource(id = R.drawable.ic_menu), contentDescription = null
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, top = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            AuthContent(
                Modifier.padding(horizontal = 36.dp),
                email = uiState.email,
                password = uiState.password,
                onSignIn = {
                    viewModel.signIn { isValid ->
                        if (isValid) {
                            navigator.navigate(CardsScreenDestination)
                        }
                    }
                },
                onCreate = {
                    viewModel.createAccount {
                        navigator.navigate(CardsScreenDestination)
                    }
                },
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange
            )

            when (uiState.uiType) {
                UiType.NORMAL -> {}
                UiType.SING_IN -> {
//                    SignInDialog(
//                        Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 36.dp),
//                        onSignIn = {
//                            viewModel.signIn {isValid->
//                                if (isValid){
//                                    navigator.navigate(CardsScreenDestination)
//                                }
//                            }
//                        },
//                        password = uiState.password,
//                        onPasswordChange = viewModel::onPasswordChange
//                    )
                }

                UiType.CREATE -> {
//                    CreateAccountDialog(
//                        Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 36.dp),
//                        onCreate = {
//                            viewModel.createAccount {
//                                navigator.navigate(CardsScreenDestination)
//                            }
//                        },
//                        password = uiState.password,
//                        onPasswordChange = viewModel::onPasswordChange
//                    )
                }
            }
        }
    }
}

@Composable
fun CreateAccountDialog(
    modifier: Modifier,
    password: String,
    onCreate: () -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = CardContainer)
        ) {
            PasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 64.dp, horizontal = 24.dp)
                    .advancedShadow(shadowBlurRadius = 4.dp, offsetY = 4.dp),
                value = password,
                label = stringResource(id = R.string.password),
                onValueChanged = onPasswordChange
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.create_your_password_6_characters),
                style = MaterialTheme.typography.titleSmall
            )
            Divider(
                color = Teal, modifier = Modifier
                    .height(2.dp)
            )
        }

        Button(
            onClick = onCreate,
            colors = ButtonDefaults.buttonColors(containerColor = CardContainer),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun SignInDialog(
    modifier: Modifier,
    password: String,
    onSignIn: () -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = CardContainer)
        ) {
            PasswordField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 64.dp, horizontal = 24.dp)
                    .advancedShadow(shadowBlurRadius = 4.dp, offsetY = 4.dp),
                value = password,
                label = stringResource(id = R.string.password),
                onValueChanged = onPasswordChange
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.enter_your_password_6_characters),
                style = MaterialTheme.typography.titleSmall
            )
            Divider(
                color = Teal, modifier = Modifier
                    .height(2.dp)
            )
        }

        ActionButton(textId = R.string.sign_in, onClick = onSignIn)
    }
}

@Composable
fun AuthContent(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    onSignIn: () -> Unit,
    onCreate: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.g_bank),
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        EmailField(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth(),
            value = email,
            onValueChanged = onEmailChange
        )

        PasswordField(
            modifier = Modifier
                .padding(top = 36.dp)
                .fillMaxWidth(),
            value = password,
            label = stringResource(R.string.password),
            onValueChanged = onPasswordChange
        )

        Divider(
            color = Teal, modifier = Modifier
                .padding(vertical = 80.dp)
                .height(2.dp)
        )

        ActionButton(textId = R.string.sign_in, onClick = onSignIn)

        Button(
            onClick = onCreate,
            colors = ButtonDefaults.buttonColors(containerColor = CardContainer),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    value: String,
    radius: Dp = 4.dp,
    onValueChanged: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier
            .background(CardContainer, RoundedCornerShape(radius))
            .padding(vertical = 8.dp, horizontal = 10.dp),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            textAlign = TextAlign.Center,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        decorationBox = { innerTextField ->
            if (value.isEmpty())
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.AccountBox, contentDescription = null,
                        tint = Color.White
                    )
                    Text(
                        text = stringResource(R.string.email),
                        style = MaterialTheme.typography.titleMedium.copy(
                            textAlign = TextAlign.Center,
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            innerTextField.invoke()
        }
    )
}

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    radius: Dp = 4.dp,
    onValueChanged: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier
            .background(CardContainer, RoundedCornerShape(radius))
            .padding(vertical = 8.dp, horizontal = 10.dp),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            textAlign = TextAlign.Center,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        decorationBox = { innerTextField ->
            if (value.isEmpty())
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        letterSpacing = (8).sp
                    )
                )
            innerTextField.invoke()
        }
    )
}