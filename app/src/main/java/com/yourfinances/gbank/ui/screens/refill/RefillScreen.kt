package com.yourfinances.gbank.ui.screens.refill

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.R
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.data.fake
import com.yourfinances.gbank.ui.screens.card.ActionButton
import com.yourfinances.gbank.ui.screens.component.BackgroundWithTop
import com.yourfinances.gbank.ui.screens.component.Card
import com.yourfinances.gbank.ui.screens.details.BillCard2
import com.yourfinances.gbank.ui.screens.menu.MenuScreen
import com.yourfinances.gbank.ui.screens.pay.CardField20
import com.yourfinances.gbank.ui.theme.GbankTheme

@Destination(
    navArgsDelegate = CardModel::class
)
@Composable
fun RefillScreen(
    navigator: DestinationsNavigator,
    cardModel: CardModel,
    viewModel: RefillViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = RefillViewModelFactory(
            LocalContext.current
        )
    )
) {
    var isMenu by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(cardModel) {
        viewModel.setCard(cardModel)
    }

    val uiState by viewModel.uiState.collectAsState()

    if (isMenu) {
        MenuScreen(navigator)
    } else {
        BackgroundWithTop(navigator, onMenuClick = { isMenu = true }) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Card(cardInfo = cardModel)
                    CardField20(
                        label = stringResource(id = R.string.enter_the_amount),
                        value = uiState.amount,
                        radius = 20.dp,
                        onValueChanged = viewModel::onAmountSet
                    )
                }

                ActionButton(textId = R.string.refill, modifier = Modifier.width(160.dp)) {
                    viewModel.fill(onSuccess = navigator::navigateUp)
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
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