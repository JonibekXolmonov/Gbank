package com.yourfinances.gbank.ui.screens.pay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.R
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.data.isZero
import com.yourfinances.gbank.ui.screens.card.ActionButton
import com.yourfinances.gbank.ui.screens.component.BackgroundWithTop
import com.yourfinances.gbank.ui.screens.component.Card
import com.yourfinances.gbank.ui.screens.details.BillCard2
import com.yourfinances.gbank.ui.screens.menu.MenuScreen
import com.yourfinances.gbank.ui.theme.CardContainer
import com.yourfinances.gbank.ui.theme.GbankTheme

@Destination(
    navArgsDelegate = CardModel::class
)
@Composable
fun PaymentScreen(
    navigator: DestinationsNavigator,
    cardModel: CardModel,
    viewModel: PaymentViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = PaymentViewModelFactory(
            LocalContext.current
        )
    )
) {

    val uiState by viewModel.uiState.collectAsState()

    var isMenu by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(cardModel) {
        viewModel.setCard(cardModel)
    }


    if (isMenu) {
        MenuScreen(navigator)
    } else {
        BackgroundWithTop(navigator, onMenuClick = { isMenu = true }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Card(cardInfo = cardModel)
                    CardField20(
                        label = stringResource(R.string.enter_the_amount),
                        value = uiState.bill.amount.isZero { "" },
                        radius = 20.dp,
                        onValueChanged = viewModel::amount
                    )
                    CardField20(
                        label = stringResource(R.string.card_phone_number),
                        value = uiState.bill.receiver,
                        keyboardType = KeyboardType.Phone,
                        radius = 20.dp,
                        onValueChanged = viewModel::receiver
                    )

                    ActionButton(textId = R.string.pay_now) {
                        viewModel.pay(uiState.bill)
                    }
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 60.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(uiState.bills) { bill ->
                        BillCard2(bill = bill)
                    }
                }
            }
        }
    }
}

@Composable
fun CardField20(
    value: String,
    radius: Dp = 4.dp,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Number,
    onValueChanged: (String) -> Unit
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .background(CardContainer, RoundedCornerShape(radius))
            .padding(vertical = 8.dp, horizontal = 14.dp),
        textStyle = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        decorationBox = { innerTextField ->
            if (value.isEmpty())
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
                )
            innerTextField.invoke()
        },
        singleLine = true
    )
}