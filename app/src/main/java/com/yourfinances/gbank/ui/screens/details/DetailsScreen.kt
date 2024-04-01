package com.yourfinances.gbank.ui.screens.details

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.yourfinances.gbank.R
import com.yourfinances.gbank.data.BillModel
import com.yourfinances.gbank.data.CardModel
import com.yourfinances.gbank.data.fake
import com.yourfinances.gbank.data.formatFloat
import com.yourfinances.gbank.ui.screens.card.ActionButton
import com.yourfinances.gbank.ui.screens.component.BackgroundWithTop
import com.yourfinances.gbank.ui.screens.component.DarkBlueCard
import com.yourfinances.gbank.ui.screens.menu.MenuScreen
import com.yourfinances.gbank.ui.theme.GbankTheme
import com.yourfinances.gbank.ui.theme.Teal
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourfinances.gbank.ui.screens.destinations.PaymentScreenDestination
import com.yourfinances.gbank.ui.screens.destinations.RefillScreenDestination

@Destination(
    navArgsDelegate = CardModel::class
)
@Composable
fun DetailsScreen(
    navigator: DestinationsNavigator,
    cardModel: CardModel,
    context: Context = LocalContext.current,
    viewModel: DetailsViewModel = viewModel(
        factory = DetailsViewModelFactory(context)
    )
) {
    var isMenu by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(cardModel) {
        viewModel.getBills(cardModel)
    }

    val uiState by viewModel.uiState.collectAsState()

    if (isMenu) {
        MenuScreen(navigator)
    } else {
        BackgroundWithTop(navigator, onMenuClick = { isMenu = true }) {
            Column(Modifier.padding(bottom = 100.dp)) {
                uiState.billInDetails?.let {
                    BillCard(it)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(36.dp)
                ) {
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        textId = R.string.pay_now,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        navigator.navigate(PaymentScreenDestination(cardModel))
                    }

                    ActionButton(
                        modifier = Modifier.weight(1f),
                        textId = R.string.refill,
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        navigator.navigate(RefillScreenDestination(cardModel))
                    }
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    items(uiState.bills) { bill ->
                        BillCard2(bill = bill, onClick = viewModel::onBillChosen)
                    }
                }
            }
        }
    }
}

@Composable
fun BillCard(bill: BillModel) {
    DarkBlueCard(Modifier.fillMaxWidth(), radius = 12.dp) {
        Column(
            Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = bill.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Teal
                )
            }

            Text(
                text = stringResource(R.string.successful_payment),
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF919EC1))
            )

            Text(
                text = "$ ${bill.amount.toFloat().formatFloat()}",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun BillCard2(
    modifier: Modifier = Modifier,
    bill: BillModel = fake(),
    onClick: (BillModel) -> Unit = {}
) {
    DarkBlueCard(modifier.width(150.dp), radius = 12.dp, onClick = {
        onClick(bill)
    }) {
        Column(
            Modifier.padding(24.dp, 24.dp, 8.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = bill.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Teal
                )
            }

            Text(
                text = "$ ${bill.amount.toFloat().formatFloat()}",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}